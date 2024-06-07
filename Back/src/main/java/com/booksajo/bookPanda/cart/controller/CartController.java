package com.booksajo.bookPanda.cart.controller;


import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.dto.CartItemDto;
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

    // 카트(장바구니) 조회
    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 카트의 아이템(책) 조회
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    // 카트에 아이템 추가
    @PostMapping("/items")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookSalesId) {
        Long userId = ((User) userDetails).getId();
        Cart cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // Cart 페이지를 벗어날 때 장바구니 변경사항 DB에 반영
    @PostMapping("/save")
    public ResponseEntity<Void> saveCartState(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CartItemDto> cartItems) {
        Long userId = ((User) userDetails).getId();
        cartService.saveCartState(userId, cartItems);
        return ResponseEntity.ok().build();
    }




}
