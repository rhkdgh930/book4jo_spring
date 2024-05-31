package com.booksajo.bookPanda.payment.domain.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "book_info")
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "publish_date", nullable = false)
    private String publishDate;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "info", nullable = false)
    private String info;

    @Column(name = "category", nullable = false)
    private String category;


}
