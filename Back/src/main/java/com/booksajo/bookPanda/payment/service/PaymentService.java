package com.booksajo.bookPanda.payment.service;

import com.booksajo.bookPanda.payment.dto.PaymentRequestDto;
import com.booksajo.bookPanda.payment.dto.PaymentResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    String getToken();
    ResponseEntity<Map> getPaymentInfo(String impUid);
    ResponseEntity<Map> verifyPayment(String impUid);
    ResponseEntity<Map<String, Object>> cancelPayment(String impUid);
    ResponseEntity<List<Map<String, Object>>> getAllPayments();
    PaymentResponseDto savePayment(PaymentRequestDto requestDto);
}
