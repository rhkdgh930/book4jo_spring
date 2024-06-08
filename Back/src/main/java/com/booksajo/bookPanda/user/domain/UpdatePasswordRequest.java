package com.booksajo.bookPanda.user.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,16}",
        message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String newPassword;
}
