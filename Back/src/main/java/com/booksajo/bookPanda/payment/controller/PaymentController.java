package com.booksajo.bookPanda.payment.controller;

import com.booksajo.bookPanda.payment.dto.PaymentRequestDto;
import com.booksajo.bookPanda.payment.dto.PaymentResponseDto;
import com.booksajo.bookPanda.payment.service.PaymentService;
import com.booksajo.bookPanda.payment.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @GetMapping("/token")
    public ResponseEntity<String> getTokenEndpoint() {
        String token = paymentService.getToken();
        return ResponseEntity.ok(token);
    }

    //-----------------------------------------------
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        try {
            String token = paymentService.getToken();
            return ResponseEntity.ok(Map.of("access_token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "액세스 토큰을 가져오는 동안 오류 발생: " + e.getMessage()));
        }
    }
    //-----------------------------------------------


    @GetMapping("/info/{impUid}")
    public ResponseEntity<?> getPaymentInfo(@PathVariable("impUid") String impUid) {
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
    public ResponseEntity<?> verifyPayment(@PathVariable("impUid") String impUid) {
        ResponseEntity<Map> response = paymentService.verifyPayment(impUid);
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
            return ResponseEntity.ok(responseMap);
        } else {
            return ResponseEntity.badRequest().body("Invalid response from Iamport");
        }
    }

//    @PostMapping("/cancel/{impUid}")
//    public ResponseEntity<?> cancelPayment(@PathVariable("impUid") String impUid) {
//        return paymentService.cancelPayment(impUid);
//    }
//    @PostMapping("/cancel")
//    public ResponseEntity<Map<String, Object>> cancelPayment(@RequestBody Map<String, Object> cancelData) {
//        String impUid = (String) cancelData.get("imp_uid");
//        return paymentService.cancelPayment(impUid);
//    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestBody Map<String, Object> request) {
        logger.debug("결제 취소 요청 데이터: {}", request);
        String impUid = (String) request.get("imp_uid");
        String merchantUid = (String) request.get("merchant_uid");

        // amount 값을 명시적으로 Double로 변환하여 처리
        Double amount = null;
        try {
            Number amountNumber = (Number) request.get("amount");
            amount = amountNumber.doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            logger.error("amount 값을 Double로 변환할 수 없습니다.", e);
            return ResponseEntity.badRequest().body("amount 값을 Double로 변환할 수 없습니다.");
        }

        logger.info("결제 취소 요청 수신. impUid: {}, merchantUid: {}, amount: {}", impUid, merchantUid, amount);

        try {
            String accessToken = paymentService.getToken();
            paymentService.cancelPayment(accessToken, impUid, merchantUid, amount);
            return ResponseEntity.ok("결제 취소 성공");
        } catch (Exception e) {
            logger.error("결제 취소 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 취소 중 오류 발생: " + e.getMessage());
        }
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

//    @GetMapping("/user/{email}")
//    public ResponseEntity<List<PaymentResponseDto>> getAllPaymentsByUser(@PathVariable("email") String userEmail) {
//        return paymentService.getAllPaymentsByUser(userEmail);
//    }
}