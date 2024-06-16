package com.booksajo.bookPanda.payment.domain;

import com.booksajo.bookPanda.order.domain.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String impUid;
    private String merchantUid;
    private int amount;
//    private String buyerName;
//    private String buyerEmail;
//    private String buyerAddr;
//    private String buyerPostcode;
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;




}
