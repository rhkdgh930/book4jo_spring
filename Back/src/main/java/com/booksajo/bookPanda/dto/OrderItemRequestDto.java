package com.booksajo.bookPanda.dto;

import com.booksajo.bookPanda.domain.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    private Long id;
    private int quantity;
    private Order order;
    private BookSales bookSales;
}
