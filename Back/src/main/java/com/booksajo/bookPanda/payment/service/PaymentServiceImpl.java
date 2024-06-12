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

    private String accessToken = null;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RestTemplate restTemplate, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public String getToken() {
        if (accessToken != null && !accessToken.isEmpty()) {
            logger.debug("캐시된 액세스 토큰 사용 중");
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
            logger.debug("액세스 토큰 요청을 보냄");
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            logger.debug("응답 받음: {}", response);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                if (responseBody.containsKey("response")) {
                    Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
                    if (responseMap.containsKey("access_token")) {
                        accessToken = (String) responseMap.get("access_token");
                        logger.info("액세스 토큰 성공적으로 가져옴");
                        return accessToken;
                    } else {
                        logger.error("응답 본문에 'access_token' 키가 없음");
                        throw new RuntimeException("응답 본문에 'access_token' 키가 없음");
                    }
                } else {
                    logger.error("응답 본문에 'response' 키가 없음");
                    throw new RuntimeException("응답 본문에 'response' 키가 없음");
                }
            } else {
                logger.error("액세스 토큰 요청 실패, 상태 코드: {}, 응답 본문: {}", response.getStatusCode(), response.getBody());
                throw new RuntimeException("액세스 토큰 요청 실패");
            }
        } catch (Exception e) {
            logger.error("액세스 토큰을 가져오는 동안 예외 발생", e);
            throw new RuntimeException("액세스 토큰을 가져오는 동안 예외 발생", e);
        }
    }

    @Override
    public ResponseEntity<Map> getPaymentInfo(String impUid) {
        String url = apiUrl + "/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            logger.debug("impUid: {}의 결제 정보를 요청 중", impUid);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            logger.info("impUid: {}의 결제 정보를 성공적으로 가져옴", impUid);
            return response;
        } catch (HttpClientErrorException e) {
            logger.error("impUid: {}의 결제 정보를 가져오는 동안 HttpClientErrorException 발생", impUid, e);
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", "결제 정보를 가져오는 동안 오류 발생: " + e.getMessage()));
        } catch (HttpServerErrorException e) {
            logger.error("impUid: {}의 결제 정보를 가져오는 동안 HttpServerErrorException 발생", impUid, e);
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", "결제 정보를 가져오는 동안 오류 발생: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("impUid: {}의 결제 정보를 가져오는 동안 예외 발생", impUid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제 정보를 가져오는 동안 오류 발생: " + e.getMessage()));
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
            logger.error("결제를 취소하는 동안 액세스 토큰을 가져오지 못함");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "액세스 토큰을 가져오지 못함"));
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
            logger.debug("impUid: {}의 결제를 취소하는 요청을 보냄", impUid);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
            logger.info("응답 상태 코드: {}", response.getStatusCode());
            logger.info("응답 본문: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody.containsKey("response") && responseBody.get("response") != null) {
                    logger.info("impUid: {}의 결제 성공적으로 취소됨", impUid);
                    return ResponseEntity.ok(responseBody);
                } else {
                    logger.error("결제 취소 실패, 응답: {}", responseBody);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제 취소 실패", "response", responseBody));
                }
            } else {
                logger.error("결제 취소 실패, 상태 코드: {}, 응답: {}", response.getStatusCode(), response.getBody());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제 취소 실패", "response", response.getBody()));
            }
        } catch (Exception e) {
            logger.error("impUid: {}의 결제를 취소하는 동안 예외 발생", impUid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제 취소 실패"));
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
            logger.debug("모든 결제 정보를 가져오는 요청을 URL: {}로 보냄", url);
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            logger.info("응답 상태 코드: {}", response.getStatusCode());
            logger.info("응답 본문: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                logger.warn("Iamport API에서 204 No Content 응답을 받음");
            }

            return response;
        } catch (Exception e) {
            logger.error("모든 결제 정보를 가져오는 동안 예외 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public PaymentResponseDto savePayment(PaymentRequestDto requestDto) {
        ResponseEntity<Map> response = getPaymentInfo(requestDto.getImpUid());
        Map<String, Object> responseBody = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK) {
            logger.error("결제 정보 가져오는 중 오류 발생: {}", response);
            throw new RuntimeException("Iamport로부터 잘못된 응답");
        }

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
            throw new RuntimeException("Iamport로부터 잘못된 응답");
        }
    }
}
