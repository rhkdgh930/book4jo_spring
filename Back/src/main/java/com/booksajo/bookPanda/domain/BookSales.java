package com.booksajo.bookPanda.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_sales_id", nullable = false)
    private Long id;

    @Column(name = "visit_count",nullable = false)
    private Integer visitCount;

    @Column(name = "sell_count" , nullable = false)
    private Integer sellCount;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}