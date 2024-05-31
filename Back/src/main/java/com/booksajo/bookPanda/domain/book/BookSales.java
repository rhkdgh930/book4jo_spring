package com.booksajo.bookPanda.domain.book;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_count",nullable = false)
    private Integer visitCount;

    @Column(name = "sell_count" , nullable = false)
    private Integer sellCount;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_info")
    private BookInfo bookInfo;
}
