package com.booksajo.bookPanda.service;

import com.booksajo.bookPanda.JWT.JwtToken;
import com.booksajo.bookPanda.dto.SignUpDto;
import com.booksajo.bookPanda.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    JwtToken signIn(String userEmail, String userPassword);

    @Transactional
    UserDto signUp(SignUpDto signUpDto);
}
