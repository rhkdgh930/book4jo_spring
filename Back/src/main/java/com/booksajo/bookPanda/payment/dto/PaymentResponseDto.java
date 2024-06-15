package com.booksajo.bookPanda.payment.dto;

import com.booksajo.bookPanda.payment.domain.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private String impUid;
    private String merchantUid;
    private int amount;
//    private String buyerName;
//    private String buyerEmail;
//    private String buyerAddr;
//    private String buyerPostcode;
    private String status;

    public PaymentResponseDto(Payment payment) {
        this.impUid = payment.getImpUid();
        this.merchantUid = payment.getMerchantUid();
        this.amount = payment.getAmount();
//        this.buyerName = payment.getBuyerName();
//        this.buyerEmail = payment.getBuyerEmail();
//        this.buyerAddr = payment.getBuyerAddr();
//        this.buyerPostcode = payment.getBuyerPostcode();
        this.status = payment.getStatus();
    }
}