package com.booksajo.bookPanda.dto;

import com.booksajo.bookPanda.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String userEmail;
    private String userPassword;
    private String userName;
    private String address;
    private List<String> roles;

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
            .userEmail(userEmail)
            .userPassword(userPassword)
            .userName(userName)
            .address(address)
            .build();
    }

}
