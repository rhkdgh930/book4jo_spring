package com.booksajo.bookPanda.cart.controller;


import com.booksajo.bookPanda.cart.domain.Cart;
import com.booksajo.bookPanda.cart.dto.CartItemDto;
import com.booksajo.bookPanda.cart.dto.CartOrderResponseDto;
import com.booksajo.bookPanda.cart.dto.CartResponseDto;
import com.booksajo.bookPanda.cart.service.CartService;
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
@RequestMapping("/api/cart")
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
                .orElseThrow(()-> new UsernameNotFoundException("User " + userEmail + " not found"));
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
                .orElseThrow(()-> new UsernameNotFoundException("User " + userEmail + " not found"));
        Long userId = user.getId();
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        System.out.println(cartItems);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getCartOrder(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String userEmail = userDetails.getUsername();
        CartOrderResponseDto responseDto = cartService.getCartOrder(userEmail);
        List<CartItemDto> cartItems = responseDto.getCartItems();
        for(CartItemDto itemDto : cartItems){
            System.out.println(itemDto);
        }
        System.out.println(responseDto.getTotalPrice());
        System.out.println(responseDto.getUserAddress());

        return ResponseEntity.ok(responseDto);
    }

    // 카트에 아이템 추가
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("id") Long bookSalesId) {
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(()-> new UsernameNotFoundException("User " + userEmail + " not found"));
        Long userId = user.getId();

        CartResponseDto cart = cartService.addCartItem(userId, bookSalesId);
        return ResponseEntity.ok(cart);
    }

    // Cart 페이지를 벗어날 때 장바구니 변경사항 DB에 반영
    @PostMapping("/save")
    public ResponseEntity<Void> saveCartState(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<CartItemDto> cartItems) {
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User " + userEmail + " not found"));
        Long userId = user.getId();
        cartService.saveCartState(userId, cartItems);
        return ResponseEntity.ok().build();
    }


}
