package com.booksajo.bookPanda.payment.controller;

import com.booksajo.bookPanda.payment.dto.PaymentRequestDto;
import com.booksajo.bookPanda.payment.dto.PaymentResponseDto;
import com.booksajo.bookPanda.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/token")
    public ResponseEntity<String> getTokenEndpoint() {
        String token = paymentService.getToken();
        return ResponseEntity.ok(token);
    }

    @GetMapping("/info/{impUid}")
    public ResponseEntity<?> getPaymentInfo(@PathVariable String impUid) {
        ResponseEntity<Map> response = paymentService.getPaymentInfo(impUid);
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
            return ResponseEntity.ok(responseMap);
        } else {
            return ResponseEntity.badRequest().body("Invalid response from Iamport");
        }
    }

    @PostMapping("/verify/{impUid}")
    public ResponseEntity<?> verifyPayment(@PathVariable String impUid) {
        ResponseEntity<Map> response = paymentService.verifyPayment(impUid);
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
            return ResponseEntity.ok(responseMap);
        } else {
            return ResponseEntity.badRequest().body("Invalid response from Iamport");
        }
    }

    @PostMapping("/cancel/{impUid}")
    public ResponseEntity<?> cancelPayment(@PathVariable String impUid) {
        return paymentService.cancelPayment(impUid);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments() {
        ResponseEntity<List<Map<String, Object>>> response = paymentService.getAllPayments();
        List<Map<String, Object>> responseBody = response.getBody();
        if (responseBody != null && !responseBody.isEmpty()) {
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentResponseDto> savePayment(@RequestBody PaymentRequestDto requestDto) {
        try {
            PaymentResponseDto savedPayment = paymentService.savePayment(requestDto);
            return ResponseEntity.ok(savedPayment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}