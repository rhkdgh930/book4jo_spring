package com.booksajo.bookPanda.product.entity;


import com.booksajo.bookPanda.product.dto.BookInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "description", length = 1000))
    })
    @Column(name = "book_info", nullable = false)
    private BookInfo bookInfo;
}
