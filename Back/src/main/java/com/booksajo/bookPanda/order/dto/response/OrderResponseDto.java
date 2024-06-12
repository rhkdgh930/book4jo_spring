package com.booksajo.bookPanda.order.dto.response;

import com.booksajo.bookPanda.order.constant.Status;
import com.booksajo.bookPanda.order.domain.Order;
import com.booksajo.bookPanda.user.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;
    private Status status;
    private String userName;
    private String userAddress;

    public OrderResponseDto(Order entity){
        this.id = entity.getId();
        this.orderDate = entity.getOrderDate();
        this.totalPrice = entity.getTotalPrice();
        this.status = entity.getStatus();
        this.userName = entity.getUser().getName();
        this.userAddress = entity.getUser().getAddress();
    }

    @Override
    public String toString() {
        return "OrderResponseDto{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
