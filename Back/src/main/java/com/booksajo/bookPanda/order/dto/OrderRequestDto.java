package com.booksajo.bookPanda.order.dto;

import com.booksajo.bookPanda.order.constant.Status;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;
    private Status status;
//    private User user;
    private String userEmail;

//    public void setUser(User user) {
//        this.user = user;
//        this.userEmail = user.getUserEmail();
//    }
}
