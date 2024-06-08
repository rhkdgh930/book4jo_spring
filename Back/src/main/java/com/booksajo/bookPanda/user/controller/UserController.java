package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.JWT.JwtToken;
import com.booksajo.bookPanda.user.dto.SignInDto;
import com.booksajo.bookPanda.user.dto.SignUpDto;
import com.booksajo.bookPanda.user.dto.UserDto;
import com.booksajo.bookPanda.user.service.RedisService;
import com.booksajo.bookPanda.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final RedisService redisService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String userEmail = signInDto.getUserEmail();
        String userPassword = signInDto.getUserPassword();
        JwtToken jwtToken = userServiceImpl.signIn(userEmail, userPassword);
        log.info("request userEmail = {}, password = {}", userEmail, userPassword);
        log.info("JwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        String email = signUpDto.getUserEmail();
        // 레디스에 인증상태를 저장
        String verified = redisService.getData(email + "_verified");

        if (verified == null || !verified.equals("true")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증이 완료되지 않았습니다.");
        }
        UserDto savedUserDto = userServiceImpl.signUp(signUpDto);
        // 회원가입 후 인증 상태 제거
        redisService.deleteData(email + "_verified");

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}
