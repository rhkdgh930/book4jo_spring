package com.booksajo.bookPanda.order.controller;

import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderItemResponseDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.service.OrderItemService;
import com.booksajo.bookPanda.order.service.OrderService;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> postOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("id") Long bookId, @RequestBody OrderRequestDto requestDto){
        try {
            String userEmail = userDetails.getUsername();
            OrderResponseDto order=orderService.createOrder(bookId, requestDto, userEmail);
            System.out.println("responseDto : " + order);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //카트에서 주문
    @PostMapping("/orders")
    public ResponseEntity<?> postCartOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrderRequestDto requestDto){
        try{
            String userEmail = userDetails.getUsername();
            OrderResponseDto order = orderService.createCartOrder(userEmail, requestDto);
            System.out.println(order);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //주문 정보 받아옴.
    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@RequestParam("orderId") Long orderId){
        try{
            System.out.println("OrderController.getOrder");
            System.out.println("orderId = " + orderId);
            OrderResponseDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //현재 사용자의 주문 내역을 조회
//    @GetMapping("/orders")
//    public ResponseEntity<?> getOrdersMember(Principal principal) {
//        Long userId = getUserIdFromPrincipal(principal);
//        List<OrderResponseDto> orderHists = orderService.getOrderHist(userI);
//        return ResponseEntity.ok(orderHists);
//    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("주문 취소");
    }

    @GetMapping("/order/items")
    public ResponseEntity<?> getOrderItems(@RequestParam("orderId") Long orderId){
        List<OrderItemResponseDto> orderItems = orderItemService.getOrderItems(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> editPost(@RequestParam("orderId") Long orderId) {
        try {
            OrderResponseDto responseDto = orderService.updateOrderStatus(orderId);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        return user.getId();
    }
}
