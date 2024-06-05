package com.booksajo.bookPanda.payment.controller;

import com.booksajo.bookPanda.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}