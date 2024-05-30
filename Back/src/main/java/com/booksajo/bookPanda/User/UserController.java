package com.booksajo.bookPanda.User;

import com.booksajo.bookPanda.JWT.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String userEmail = signInDto.getUserEmail();
        String userPassword = signInDto.getUserPassword();
        JwtToken jwtToken = userService.signIn(userEmail, userPassword);
        log.info("request userEmail = {}, password = {}", userEmail, userPassword);
        log.info("JwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }
}
