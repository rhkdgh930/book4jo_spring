package com.booksajo.bookPanda.order.dto.response;

import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.order.domain.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private int quantity;
    private Order order;
//    private BookSales bookSales;

    public OrderItemResponseDto(OrderItem entity){
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.order = entity.getOrder();
//        this.bookSales = entity.getBookSales();
    }
}
