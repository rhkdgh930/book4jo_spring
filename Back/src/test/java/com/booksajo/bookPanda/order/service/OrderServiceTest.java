package com.booksajo.bookPanda.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.cart.domain.CartItem;
import com.booksajo.bookPanda.cart.repository.CartItemRepository;
import com.booksajo.bookPanda.cart.repository.CartRepository;
import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.repository.OrderItemRepository;
import com.booksajo.bookPanda.order.repository.OrderRepository;
import com.booksajo.bookPanda.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookSalesRepository bookSalesRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder_Success() {
        Long bookId = 1L;
        BookSales bookSales = mock(BookSales.class);
        User user = mock(User.class);
        OrderRequestDto requestDto = new OrderRequestDto();
        when(bookSalesRepository.findById(bookId)).thenReturn(Optional.of(bookSales));
        when(authentication.getPrincipal()).thenReturn(user);
        when(bookSales.getStock()).thenReturn(10);
        when(bookSales.getBookInfo().getDiscount()).thenReturn("1000");

        OrderResponseDto responseDto = orderService.createOrder(bookId, requestDto, authentication);

        assertNotNull(responseDto);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testCreateOrder_BookNotFound() {
        Long bookId = 1L;
        OrderRequestDto requestDto = new OrderRequestDto();
        when(bookSalesRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(bookId, requestDto, authentication));

        System.out.println(exception.getMessage());

        assertEquals("책이 없습니다.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_OutOfStock() {
        Long bookId = 1L;
        BookSales bookSales = mock(BookSales.class);
        OrderRequestDto requestDto = new OrderRequestDto();
        when(bookSalesRepository.findById(bookId)).thenReturn(Optional.of(bookSales));
        when(bookSales.getStock()).thenReturn(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(bookId, requestDto, authentication));

        assertEquals("품절된 상품입니다.", exception.getMessage());
    }

    @Test
    public void testCreateCartOrder_Success() {
        Long cartId = 1L;
        User user = mock(User.class);
        OrderRequestDto requestDto = new OrderRequestDto();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = mock(CartItem.class);
        cartItems.add(cartItem);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cartItemRepository.findAllByCartId(cartId)).thenReturn(cartItems);
        when(cartItem.getBookSales()).thenReturn(mock(BookSales.class));
        when(cartItem.getBookSales().getBookInfo().getDiscount()).thenReturn("1000");
        when(cartItem.getQuantity()).thenReturn(1);

        OrderResponseDto responseDto = orderService.createCartOrder(cartId, requestDto, authentication);

        assertNotNull(responseDto);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testGetOrderHist() {
        Long userId = 1L;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderRepository.findAllByUserId(userId)).thenReturn(orders);

        List<OrderResponseDto> responseDtos = orderService.getOrderHist(userId);

        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
        verify(orderRepository, times(1)).findAllByUserId(userId);
    }

    // Additional tests for methods like decreaseItemStock, isStocked, etc., can be added similarly.
}
