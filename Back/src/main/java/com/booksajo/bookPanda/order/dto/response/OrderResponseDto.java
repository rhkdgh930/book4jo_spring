package com.booksajo.bookPanda.order.dto.response;

import com.booksajo.bookPanda.order.constant.Status;
import com.booksajo.bookPanda.order.domain.Order;
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
    private String address1;
    private String address2;
    private String postCode;
    private String userPhoneNumber;

    public OrderResponseDto(Order entity){
        this.id = entity.getId();
        this.orderDate = entity.getOrderDate();
        this.totalPrice = entity.getTotalPrice();
        this.status = entity.getStatus();
        this.userName = entity.getUser().getName();
        this.address1 = entity.getAddress1();
        this.address2 = entity.getAddress2();
        this.postCode = entity.getPostCode();
        this.userPhoneNumber = entity.getUser().getPhoneNumber();
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
