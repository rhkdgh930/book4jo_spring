package com.booksajo.bookPanda.cart.service;

//import com.booksajo.bookPanda.domain.book.BookSales;
//import com.booksajo.bookPanda.domain.cart.Cart;
//import com.booksajo.bookPanda.domain.cart.CartItem;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.cart.repository.CartItemRepository;
import com.booksajo.bookPanda.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private BookSalesRepository bookSalesRepository;
//    private UserRepository userRepository;
//
//    public Cart getCartByUserId(Long userId) {
//        return cartRepository.findByUserId(userId).orElseGet(() -> createCartForUser(userId));
//    }
//
//    @Transactional
//    public Cart createCartForUser(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Cart cart = new Cart();
//        cart.setUser(user);
//        return cartRepository.save(cart);
//    }
//
//    @Transactional
//    public Cart addCartItem(Long userId, Long bookSalesId) {
//        Cart cart = getCartByUserId(userId);
//        BookSales bookSales = bookSalesRepository.findById(bookSalesId).orElseThrow(() -> new RuntimeException("Book not found"));
//
//        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//                .filter(item -> item.getBookSales().getId().equals(bookSalesId))
//                .findFirst();
//
//        if (existingCartItem.isPresent()) {
//            CartItem cartItem = existingCartItem.get();
//            cartItem.setQuantity(cartItem.getQuantity() + 1);
//            cartItemRepository.save(cartItem);
//        } else {
//            CartItem cartItem = new CartItem();
//            cartItem.setBookSales(bookSales);
//            cartItem.setQuantity(1);
//
//            cart.addItem(cartItem);
//            cartItemRepository.save(cartItem);
//        }
//
//        return cartRepository.save(cart);
//    }
//
//    @Transactional
//    public Cart removeCartItem(Long userId, Long cartItemId) {
//        Cart cart = getCartByUserId(userId);
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
//        cart.removeItem(cartItem);
//        cartItemRepository.delete(cartItem);
//        return cartRepository.save(cart);
//    }
//
//    public List<CartItem> getCartItems(Long userId) {
//        Cart cart = getCartByUserId(userId);
//        return cart.getCartItems();
//    }
//
//    public int getCartItemCount(Long userId) {
//        Cart cart = getCartByUserId(userId);
//        return cart.getCartItems().size();
//    }
}
