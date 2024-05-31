package com.booksajo.bookPanda.payment.domain.payment;

import com.booksajo.bookPanda.payment.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "payment_uid", nullable = false)
    private String paymentUid;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}
