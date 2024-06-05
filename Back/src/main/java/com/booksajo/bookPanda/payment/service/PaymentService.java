package com.booksajo.bookPanda.payment.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentService {
    String getToken();
    ResponseEntity<Map> getPaymentInfo(String impUid);
    ResponseEntity<Map> verifyPayment(String impUid);
}
