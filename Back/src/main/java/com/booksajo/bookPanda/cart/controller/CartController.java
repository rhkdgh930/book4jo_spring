package com.booksajo.bookPanda.cart.controller;


import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.dto.CartItemDto;
import com.booksajo.bookPanda.cart.dto.CartResponseDto;
import com.booksajo.bookPanda.cart.service.CartService;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CustomUserDetailsService userDetailsService;

    public CartController(CartService cartService, CustomUserDetailsService userDetailsService) {
        this.cartService = cartService;
        this.userDetailsService = userDetailsService;
    }

    // 카트(장바구니) 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        CartResponseDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // 카트의 아이템(책) 조회
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    // 카트에 아이템 추가
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(Principal principal, @RequestParam("id") Long bookSalesId) {
        Long userId = getUserIdFromPrincipal(principal);
        CartResponseDto cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // Cart 페이지를 벗어날 때 장바구니 변경사항 DB에 반영
    @PostMapping("/save")
    public ResponseEntity<Void> saveCartState(Principal principal, @RequestBody List<CartItemDto> cartItems) {
        Long userId = getUserIdFromPrincipal(principal);
        cartService.saveCartState(userId, cartItems);
        return ResponseEntity.ok().build();
    }


    private Long getUserIdFromPrincipal(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        return user.getId();
    }


}
