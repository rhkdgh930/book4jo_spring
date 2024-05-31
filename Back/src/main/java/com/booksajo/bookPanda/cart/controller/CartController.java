package com.booksajo.bookPanda.cart.controller;


import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.domain.CartItem;
import com.booksajo.bookPanda.cart.service.CartService;
import com.booksajo.bookPanda.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 카트 조회
    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 카트의 아이템 조회
    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    // 카트의 아이템 갯수 조회
    @GetMapping("/{userId}/items/count")
    public ResponseEntity<Integer> getCartItemsCount(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        int itemCount = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(itemCount);
    }

    // 카트에 아이템 추가
    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookSalesId) {
        Long userId = ((User) userDetails).getId();
        Cart cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // 카트에서 아이템 삭제
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long itemId) {
        Long userId = ((User) userDetails).getId();
        Cart cart = cartService.removeCartItem(userId, itemId);
        return ResponseEntity.ok(cart);
    }



}
