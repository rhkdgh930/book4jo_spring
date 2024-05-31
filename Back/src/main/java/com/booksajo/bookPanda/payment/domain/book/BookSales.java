package com.booksajo.bookPanda.payment.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "book_sales")
public class BookSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "visit_count", nullable = false)
    private Integer visitCount;

    @Column(name = "sell_count", nullable = false)
    private Integer sellCount;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookInfo bookInfo;
}
