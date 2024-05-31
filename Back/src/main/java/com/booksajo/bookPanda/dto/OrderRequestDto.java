package com.booksajo.bookPanda.dto;

import com.booksajo.bookPanda.constant.Status;
import com.booksajo.bookPanda.domain.Order;
import com.booksajo.bookPanda.domain.OrderItem;
import com.booksajo.bookPanda.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;
    private Status status;
    private User user;
    private String userEmail;

    public void setUser(User user) {
        this.user = user;
        this.userEmail = user.getUserEmail();
    }
}
