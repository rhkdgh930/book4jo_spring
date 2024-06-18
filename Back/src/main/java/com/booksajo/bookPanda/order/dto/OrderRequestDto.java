package com.booksajo.bookPanda.order.dto;

import com.booksajo.bookPanda.order.constant.Status;
import com.booksajo.bookPanda.user.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private int totalPrice;
    private Status status;
    private String statusLabel;
    private User user;
    private String shippingName;
    private String address1;
    private String address2;
    private String postCode;
    private String userPhoneNumber;


    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status){
        this.status = status;
        this.statusLabel = status.getlabel();
    }
}
