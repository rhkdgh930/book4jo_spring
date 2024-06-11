package com.booksajo.bookPanda.order.controller;

import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderItemResponseDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.service.OrderItemService;
import com.booksajo.bookPanda.order.service.OrderService;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.service.UserService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserDetailsService userDetailsService;

    //바로주문
    @PostMapping("/order")
    public ResponseEntity<?> postOrder(@RequestParam("id") Long bookId, @RequestBody OrderRequestDto requestDto,Authentication authentication, @AuthenticationPrincipal User user){
        try {
            System.out.println("userEmail : " + user.getUserEmail());
             System.out.println("Received requestDto: "+ requestDto);
            OrderResponseDto order=orderService.createOrder(bookId, requestDto, authentication);
            System.out.println("responseDto : " + order);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //카트에서 주문
    @PostMapping("/orders")
    public ResponseEntity<?> postCartOrder(@RequestBody OrderRequestDto requestDto, Authentication authentication){
        try{
            Principal principal = (Principal) authentication.getPrincipal();
            Long userId = getUserIdFromPrincipal(principal);
            OrderResponseDto order = orderService.createCartOrder(userId, requestDto, authentication);
            System.out.println(order);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //주문 정보 받아옴.
    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@RequestParam("id") Long orderId){
        try{
            OrderResponseDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //현재 사용자의 주문 내역을 조회
    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersMember(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<OrderResponseDto> orderHists = orderService.getOrderHist(userId);
        return ResponseEntity.ok(orderHists);
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam("id") Long orderId, @RequestBody OrderRequestDto requestDto){
        orderService.cancelOrder(requestDto);
        return ResponseEntity.ok("주문 취소");
    }

    @GetMapping("/items")
    public ResponseEntity<?> getOrderItems(@RequestParam("id") Long orderId, @RequestBody OrderRequestDto requestDto){
        List<OrderItemResponseDto> orderItems = orderItemService.getOrderItems(orderId);
        return ResponseEntity.ok(orderItems);
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        return user.getId();
    }
}
