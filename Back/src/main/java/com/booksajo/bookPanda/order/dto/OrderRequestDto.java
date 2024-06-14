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
    private User user;
    private String userName;
    private String address1;
    private String address2;
    private String postCode;
    private String userPhoneNumber;


    public void setUser(User user) {
        this.user = user;
        this.userName = user.getUsername();
        this.userPhoneNumber = user.getPhoneNumber();
    }
}
