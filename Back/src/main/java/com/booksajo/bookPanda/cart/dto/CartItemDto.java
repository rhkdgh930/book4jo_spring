package com.booksajo.bookPanda.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long BookSalesId;
    private String title;
    private String image;
    private int quantity;
    private String price;
    private boolean checked;
    private int stock;
}
