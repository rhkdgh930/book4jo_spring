package com.booksajo.bookPanda.User;

import com.booksajo.bookPanda.JWT.JwtToken;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    JwtToken signIn(String userEmail, String userPassword);

    @Transactional
    UserDto signUp(SignUpDto signUpDto);
}
