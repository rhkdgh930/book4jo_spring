package com.booksajo.bookPanda.book.dto;

import lombok.Data;

@Data
public class BookSalesRequest {
    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
    private SalesInfoDto salesInfoDto;
}
