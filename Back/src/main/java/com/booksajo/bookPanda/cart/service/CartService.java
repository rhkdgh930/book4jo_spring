package com.booksajo.bookPanda.cart.service;


import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.repository.BookSalesRepository;
import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.domain.CartItem;
import com.booksajo.bookPanda.cart.dto.CartItemDto;
import com.booksajo.bookPanda.cart.dto.CartResponseDto;
import com.booksajo.bookPanda.cart.repository.CartItemRepository;
import com.booksajo.bookPanda.cart.repository.CartRepository;
import com.booksajo.bookPanda.exception.errorCode.CartErrorCode;
import com.booksajo.bookPanda.exception.exception.CartException;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookSalesRepository bookSalesRepository;
    private final UserRepository userRepository;

    public CartResponseDto getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdWithItems(userId);
        return toCartResponseDto(cart);
    }

    @Transactional
    public Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    // 유저의 카트가 존재하면 카트에 아이템 추가, 존재하지 않을시 카트 생성 후 아이템 추가, 동일한 책이 존재 할 경우 수량 추가
    @Transactional
    public CartResponseDto addCartItem(Long userId, Long bookSalesId) {
        Cart cart;
        try {
            cart = cartRepository.findByUserId(userId)
                    .orElseThrow( () -> new CartException(CartErrorCode.USER_NOT_FOUND));
        } catch (CartException e) {
            if(e.getErrorCode() == CartErrorCode.USER_NOT_FOUND) {
                cart = createCartForUser(userId);
            } else {
                throw e;
            }
        }

        BookSales bookSales = bookSalesRepository.findById(bookSalesId)
                .orElseThrow(() -> new CartException(CartErrorCode.BOOK_NOT_FOUND));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getBookSales().getId().equals(bookSalesId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setBookSales(bookSales);
            cartItem.setQuantity(1);

            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
        }

        return toCartResponseDto(cart);
    }

    public List<CartItemDto> getCartItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
        return cart.getCartItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCartItemQuantity(Long userId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException(CartErrorCode.BOOK_NOT_FOUND));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new CartException(CartErrorCode.BOOK_NOT_FOUND);
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException(CartErrorCode.BOOK_NOT_FOUND));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new CartException(CartErrorCode.BOOK_NOT_FOUND);
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setBookSalesId(cartItem.getBookSales().getId());
        cartItemDto.setTitle(cartItem.getBookSales().getBookInfo().getTitle());
        cartItemDto.setImage(cartItem.getBookSales().getBookInfo().getImage());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPrice(cartItem.getBookSales().getBookInfo().getDiscount());
        cartItemDto.setStock(cartItem.getBookSales().getStock());
        cartItemDto.setChecked(true);
        return cartItemDto;
    }

    private CartResponseDto toCartResponseDto(Cart cart) {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setId(cart.getId());
        cartResponseDto.setCartItems(cart.getCartItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return cartResponseDto;
    }
}
