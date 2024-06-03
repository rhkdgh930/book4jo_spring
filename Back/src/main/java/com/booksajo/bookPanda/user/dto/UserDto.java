package com.booksajo.bookPanda.user.dto;

import com.booksajo.bookPanda.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String userEmail;
    private String userName;
    private String address;
    private String phoneNumber;

    static public UserDto toDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .userEmail(user.getUserEmail())
            .userName(user.getUsername())
            .address(user.getAddress())
            .phoneNumber(user.getPhoneNumber())
            .build();
    }

    public User toEntity() {
        return User.builder()
            .id(id)
            .userEmail(userEmail)
            .userName(userName)
            .address(address)
            .phoneNumber(phoneNumber)
            .build();
    }
}
