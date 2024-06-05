package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.JWT.JwtToken;
import com.booksajo.bookPanda.user.dto.SignInDto;
import com.booksajo.bookPanda.user.dto.SignUpDto;
import com.booksajo.bookPanda.user.dto.UserDto;
import com.booksajo.bookPanda.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        System.out.println("nnnnnnnnnnnnnnnnnnnnnn");
        String userEmail = signInDto.getUserEmail();
        String userPassword = signInDto.getUserPassword();
        JwtToken jwtToken = userServiceImpl.signIn(userEmail, userPassword);
        log.info("request userEmail = {}, password = {}", userEmail, userPassword);
        log.info("JwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto savedUserDto = userServiceImpl.signUp(signUpDto);
        return ResponseEntity.ok(savedUserDto);
    }

}
