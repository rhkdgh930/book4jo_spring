package com.booksajo.bookPanda.cart;


import com.booksajo.bookPanda.domain.cart.Cart;
import com.booksajo.bookPanda.domain.cart.CartItem;
import com.booksajo.bookPanda.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 카트 조회
    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/{userId}/items/count")
    public ResponseEntity<Integer> getCartItemsCount(@PathVariable Long userId) {
        int itemCount = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(itemCount);
    }

    // 카트에 아이템 추가
    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestParam Long bookSalesId) {
        Cart cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // 카트에서 아이템 삭제
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        Cart cart = cartService.removeCartItem(userId, itemId);
        return ResponseEntity.ok(cart);
    }



}
