package com.booksajo.bookPanda.payment.service;

import com.booksajo.bookPanda.order.repository.OrderRepository;
import com.booksajo.bookPanda.payment.domain.Payment;
import com.booksajo.bookPanda.payment.dto.PaymentRequestDto;
import com.booksajo.bookPanda.payment.dto.PaymentResponseDto;
import com.booksajo.bookPanda.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${iamport.api_key}")
    private String apiKey;

    @Value("${iamport.api_secret}")
    private String apiSecret;

    @Value("${iamport.api_url}")
    private String apiUrl;

    private String accessToken;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RestTemplate restTemplate, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public String getToken() {
        if (accessToken != null) {
            logger.debug("Using cached access token");
            return accessToken;
        }

        String url = apiUrl + "/users/getToken";
        Map<String, String> request = Map.of(
                "imp_key", apiKey,
                "imp_secret", apiSecret
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        try {
            logger.debug("Sending request to get access token");
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("response")) {
                Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
                accessToken = (String) responseMap.get("access_token");
                logger.info("Access token retrieved successfully");
                return accessToken;
            } else {
                logger.error("Failed to retrieve access token: response body is null or does not contain 'response'");
                throw new RuntimeException("Failed to retrieve access token");
            }
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving access token", e);
            throw new RuntimeException("Failed to retrieve access token", e);
        }
    }

    @Override
    public ResponseEntity<Map> getPaymentInfo(String impUid) {
        String url = apiUrl + "/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            logger.debug("Sending request to get payment info for impUid: {}", impUid);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            logger.info("Payment info retrieved successfully for impUid: {}", impUid);
            return response;
        } catch (HttpClientErrorException e) {
            logger.error("HttpClientErrorException occurred while retrieving payment info for impUid: {}", impUid, e);
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", "Failed to retrieve payment info: " + e.getMessage()));
        } catch (HttpServerErrorException e) {
            logger.error("HttpServerErrorException occurred while retrieving payment info for impUid: {}", impUid, e);
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", "Failed to retrieve payment info: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving payment info for impUid: {}", impUid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to retrieve payment info: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Map> verifyPayment(String impUid) {
        return getPaymentInfo(impUid);
    }

    @Override
    public ResponseEntity<Map<String, Object>> cancelPayment(String impUid) {
        String token = getToken();
        if (token == null) {
            logger.error("Failed to retrieve access token for cancelling payment");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Failed to retrieve access token"));
        }
        String url = apiUrl + "/payments/cancel";

        Map<String, Object> request = new HashMap<>();
        request.put("imp_uid", impUid);
        request.put("reason", "requested by user");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            logger.debug("Sending request to cancel payment for impUid: {}", impUid);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
            logger.info("Response Status Code: {}", response.getStatusCode());
            logger.info("Response Body: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("response") && responseBody.get("response") != null) {
                    logger.info("Payment canceled successfully for impUid: {}", impUid);
                    return ResponseEntity.ok(responseBody);
                } else {
                    logger.error("Failed to cancel payment, response: {}", responseBody);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to cancel payment", "response", responseBody));
                }
            } else {
                logger.error("Failed to cancel payment, status code: {}, response: {}", response.getStatusCode(), response.getBody());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to cancel payment", "response", response.getBody()));
            }
        } catch (Exception e) {
            logger.error("Exception occurred while cancelling payment for impUid: {}", impUid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to cancel payment"));
        }
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllPayments() {
        String token = getToken();
        String url = apiUrl + "/payments";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            logger.debug("Sending request to get all payments with URL: {}", url);
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            logger.info("Response Status Code: {}", response.getStatusCode());
            logger.info("Response Body: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                logger.warn("Received 204 No Content from Iamport API");
            }

            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving all payments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public PaymentResponseDto savePayment(PaymentRequestDto requestDto) {
        ResponseEntity<Map> response = getPaymentInfo(requestDto.getImpUid());
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("response")) {
            Map<String, Object> paymentInfo = (Map<String, Object>) responseBody.get("response");


            Payment payment = Payment.builder()
                    .impUid((String) paymentInfo.get("imp_uid"))
                    .merchantUid((String) paymentInfo.get("merchant_uid"))
                    .amount((Integer) paymentInfo.get("amount"))
                    .buyerName((String) paymentInfo.get("buyer_name"))
                    .buyerEmail((String) paymentInfo.get("buyer_email"))
                    .buyerAddr((String) paymentInfo.get("buyer_addr"))
                    .buyerPostcode((String) paymentInfo.get("buyer_postcode"))
                    .status((String) paymentInfo.get("status"))
                    //.order(orderRepository.findById(requestDto.getOrderId()).orElse(null))
                    .build();


            Payment savedPayment = paymentRepository.save(payment);

            return new PaymentResponseDto(savedPayment);
        } else {
            throw new RuntimeException("Invalid response from Iamport");
        }
    }
}
