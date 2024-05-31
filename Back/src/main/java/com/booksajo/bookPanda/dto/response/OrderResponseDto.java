package com.booksajo.bookPanda.dto.response;

import com.booksajo.bookPanda.domain.Order;
import com.booksajo.bookPanda.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;
    private String status;
    private String userEmail;

    public OrderResponseDto(Order entity){
        this.id = entity.getId();
        this.orderDate = entity.getOrderDate();
        this.totalPrice = entity.getTotalPrice();
        this.status = entity.getStatus();
        this.userEmail = entity.getUser().getUserEmail();
    }
}
