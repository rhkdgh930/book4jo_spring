package com.booksajo.bookPanda.domain;

import com.booksajo.bookPanda.domain.order.Order;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
