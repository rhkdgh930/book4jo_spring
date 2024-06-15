package com.booksajo.bookPanda.payment.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentRequestDto {
    private String impUid;
    private String merchantUid;
    private int amount;
    private String buyerName;
    private String buyerEmail;
    private String buyerAddr;
    private String buyerPostcode;
    private String status;
    //private Long orderId;
}
