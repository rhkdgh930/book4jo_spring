package com.booksajo.bookPanda.domain.book;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "info", nullable = false, length = 1000)
    private String info;

    @Column(name = "category", nullable = false)
    private String category;
}
