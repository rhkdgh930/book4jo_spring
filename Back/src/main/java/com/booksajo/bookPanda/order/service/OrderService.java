package com.booksajo.bookPanda.order.service;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.cart.domain.CartItem;
import com.booksajo.bookPanda.cart.repository.CartItemRepository;
import com.booksajo.bookPanda.cart.repository.CartRepository;
import com.booksajo.bookPanda.order.constant.Status;
import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.order.domain.OrderItem;
import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.repository.OrderItemRepository;
import com.booksajo.bookPanda.order.repository.OrderRepository;
import com.booksajo.bookPanda.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    OrderRepository orderRepository;
    BookSalesRepository bookSalesRepository;
    OrderItemRepository orderItemRepository;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;

    //바로 주문
    @Transactional
    public OrderResponseDto createOrder(Long bookId, OrderRequestDto requestDto, Authentication authentication) {
        BookSales book = bookSalesRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책이 없습니다."));
        if(isStocked(book)){
            User user = (User) authentication.getPrincipal();
            requestDto.setUser(user);
            requestDto.setStatus(Status.NOT_PAID);

            List<OrderItem> orderItems = new ArrayList<>();

            requestDto.setTotalPrice(Integer.parseInt(book.getBookInfo().getDiscount()));//TODO : 전체 가격 측정

            Order order = new Order(requestDto);
            orderRepository.save(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setBookSales(book);
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            orderItemRepository.saveAll(orderItems);

            return new OrderResponseDto(order);
        } else {
            throw new IllegalArgumentException("품절된 상품입니다.");
        }
    }

    //장바구니에서 주문
    @Transactional
    public OrderResponseDto createCartOrder(Long cartId, OrderRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        requestDto.setUser(user);
        requestDto.setStatus(Status.NOT_PAID);

        Order order = new Order();
        order.setStatus(Status.NOT_PAID);
        order.setUser(user);

        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);

        order.setTotalPrice(calculateTotalPrice(cartItems));

        orderRepository.save(order);

        List<OrderItem> orderItems = createOrderItemsFromCartItems(cartItems, order);

        orderItemRepository.saveAll(orderItems);

        return new OrderResponseDto(order);
    }

    //TODO : 주문 취소

    @Transactional
    private List<OrderItem> createOrderItemsFromCartItems(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            BookSales book = cartItem.getBookSales();

            if(isStocked(book)){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setBookSales(book);
                orderItem.setQuantity(cartItem.getQuantity());

                orderItems.add(orderItem);
            } else {
                throw new IllegalArgumentException(book.getBookInfo().getTitle() + "이 품절되었습니다.");
            }
        }
        return orderItems;
    }

    private int calculateTotalPrice(List<CartItem> cartItems) {
        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            int itemPrice = Integer.parseInt(cartItem.getBookSales().getBookInfo().getDiscount());//TODO : 가격 측정
            int quantity = cartItem.getQuantity();
            totalPrice += (itemPrice * quantity);
        }
        return totalPrice;
    }

    //재고 있는지 확인
    private boolean isStocked(BookSales bookSales) {
        boolean isStocked = false;
        int stock = 0;
        stock = bookSales.getStock();

        if(stock > 0) {
            isStocked = true;
        }
        return isStocked;
    }


      //TODO : 아이템 재고 줄이고 CART 초기화
//    private void decreaseItemStockAndRemoveCartItems(Long cartItemId, Long count) {
//        CartItem cartItem = cartItemRepository.findById(cartItemId)
//                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));
//
//        BookSales book = cartItem.getBookSales();
//        decreaseItemStock(book.getId(), count);
//        cartItemRepository.delete(cartItem);
//    }

    //TODO : 책 재고 줄이기
    public void decreaseItemStock(long itemId, long quantity) {
        BookSales bookSales = bookSalesRepository.findById(itemId)
                .orElseThrow(() ->new IllegalArgumentException("아이템 없음."));

        if (bookSales.getStock() >= quantity) {
            bookSales.setStock((int) (bookSales.getStock() - quantity));
            bookSalesRepository.save(bookSales);
        } else {
            throw new IllegalArgumentException("재고 부족");//재고 부족 에러코드만들기
        }
    }
}
