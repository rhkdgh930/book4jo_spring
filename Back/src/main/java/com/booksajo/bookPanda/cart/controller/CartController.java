package com.booksajo.bookPanda.cart.controller;


import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.dto.CartItemDto;
import com.booksajo.bookPanda.cart.dto.CartResponseDto;
import com.booksajo.bookPanda.cart.service.CartService;
import com.booksajo.bookPanda.exception.errorCode.CartErrorCode;
import com.booksajo.bookPanda.exception.exception.CartException;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // 카트(장바구니) 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();
        CartResponseDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 카트의 아이템(책) 조회
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        System.out.println(cartItems);
        return ResponseEntity.ok(cartItems);
    }

    // 카트에 아이템 추가
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("id") Long bookSalesId) {
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(()-> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();

        CartResponseDto cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // 아이템 수량 변경
    @PatchMapping("/items/{id}/quantity")
    public ResponseEntity<Void> updateCartItemQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestParam int quantity) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();

        cartService.updateCartItemQuantity(userId, id, quantity);
        return ResponseEntity.ok().build();
    }

    // 아이템 삭제
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();

        cartService.deleteCartItem(userId, id);
        return ResponseEntity.ok().build();
    }

    // Cart 페이지를 벗어날 때 장바구니 변경사항 DB에 반영
    @PostMapping("/save")
    public ResponseEntity<Void> saveCartState(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CartItemDto> cartItems) {
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new CartException(CartErrorCode.USER_NOT_FOUND));
        Long userId = user.getId();
        cartService.saveCartState(userId, cartItems);
        return ResponseEntity.ok().build();
    }


}
