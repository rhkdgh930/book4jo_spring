package com.booksajo.bookPanda.service;

import com.booksajo.bookPanda.domain.book.BookInfo;
import com.booksajo.bookPanda.domain.cart.Cart;
import com.booksajo.bookPanda.domain.book.BookSales;
import com.booksajo.bookPanda.repository.BookSalesRepository;
import com.booksajo.bookPanda.repository.CartItemRepository;
import com.booksajo.bookPanda.repository.CartRepository;
import com.booksajo.bookPanda.repository.UserRepository;
import com.booksajo.bookPanda.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookSalesRepository bookSalesRepository;

    @InjectMocks
    private CartService cartService;

    private User testUser;
    private BookSales testBookSales;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");

        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1L);
        bookInfo.setTitle("Book Title");
        bookInfo.setPrice("Book Price");
        bookInfo.setAuthor("Book Author");

        testBookSales = new BookSales();
        testBookSales.setId(1L);
        testBookSales.setBookInfo(bookInfo);

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    }

    @Test
    void testGetCartByUserId() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.getCartByUserId(testUser.getId());
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
    }

    @Test
    void testCreateCartForUser() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Cart result = cartService.createCartForUser(testUser.getId());
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
    }

    @Test
    void testAddNewCartItem() {
        Cart cart = new Cart();
        cart.setUser(testUser);

        when(cartRepository.findByUserId(testUser.getId())).thenReturn(Optional.of(cart));
        when(bookSalesRepository.findById(testBookSales.getId())).thenReturn(Optional.of(testBookSales));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addCartItem(testUser.getId(), testBookSales.getId());

        assertNotNull(result);
        assertEquals(1, result.getCartItems().size());
        assertEquals(testBookSales.getId(), result.getCartItems().get(0).getBookSales().getId());
        assertEquals(1, result.getCartItems().get(0).getQuantity());

    }

    @Test
    void testAddExistingCartItem() {

    }




}
