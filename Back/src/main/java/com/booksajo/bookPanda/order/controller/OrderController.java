package com.booksajo.bookPanda.order.controller;

import com.booksajo.bookPanda.order.dto.OrderRequestDto;
import com.booksajo.bookPanda.order.dto.response.OrderResponseDto;
import com.booksajo.bookPanda.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    //바로주문
    @PostMapping("/{bookId}/order")
    public ResponseEntity<?> postOrder(@PathVariable Long bookId, @RequestBody OrderRequestDto requestDto,Authentication authentication){
        try {
            OrderResponseDto order=orderService.createOrder(bookId, requestDto, authentication);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //카트에서 주문
    @PostMapping("/{cartId}/order")
    public ResponseEntity<?> postCartOrder(@PathVariable Long cardId, @RequestBody OrderRequestDto requestDto, Authentication authentication){
        try{
            OrderResponseDto order = orderService.createCartOrder(cardId, requestDto, authentication);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //현재 사용자의 주문 내역을 조회
    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrderMember(@PathVariable Long UserId) {
        List<OrderResponseDto> orderHists = orderService.getOrderHist(UserId);
        return ResponseEntity.ok(orderHists);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancleOrder(@PathVariable Long orderId, @RequestBody OrderRequestDto requestDto){
        orderService.cancelOrder(requestDto);
        return ResponseEntity.ok("주문 취소");
    }
}
