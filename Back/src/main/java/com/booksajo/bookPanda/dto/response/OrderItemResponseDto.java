package com.booksajo.bookPanda.dto.response;

import com.booksajo.bookPanda.domain.BookSales;
import com.booksajo.bookPanda.domain.CartItem;
import com.booksajo.bookPanda.domain.Order;
import com.booksajo.bookPanda.domain.OrderItem;
import com.booksajo.bookPanda.dto.OrderItemRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private int quantity;
    private Order order;
    private BookSales bookSales;

    public OrderItemResponseDto(OrderItem entity){
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.order = entity.getOrder();
        this.bookSales = entity.getBookSales();
    }
}
