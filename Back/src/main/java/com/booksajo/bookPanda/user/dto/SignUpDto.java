// SignUpDto.java
package com.booksajo.bookPanda.user.dto;

import com.booksajo.bookPanda.user.domain.User;
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
    private String phoneNumber;
    private List<String> roles;
    private String authCode;

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
            .userEmail(userEmail)
            .userPassword(userPassword)
            .userName(userName)
            .address(address)
            .phoneNumber(phoneNumber)
            .roles(roles)
            .build();
    }

}
