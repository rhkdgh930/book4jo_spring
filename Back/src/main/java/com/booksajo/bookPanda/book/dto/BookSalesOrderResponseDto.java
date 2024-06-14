package com.booksajo.bookPanda.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSalesOrderResponseDto {
    private String title;
    private String image;
    private String discount;
    private String userAddress;
    private String userName;
    private int quantity = 1;
}
