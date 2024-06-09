package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.JWT.JwtToken;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.dto.JwtDto;
import com.booksajo.bookPanda.user.dto.SignInDto;
import com.booksajo.bookPanda.user.dto.SignUpDto;
import com.booksajo.bookPanda.user.dto.UserDto;
import com.booksajo.bookPanda.user.repository.UserRepository;
import com.booksajo.bookPanda.user.service.RedisService;
import com.booksajo.bookPanda.user.service.TokenService;
import com.booksajo.bookPanda.user.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> feature/login-ms

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
        String userEmail = signInDto.getUserEmail();
        String userPassword = signInDto.getUserPassword();
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, userPassword, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtToken jwtToken = userServiceImpl.signIn(userEmail, userPassword);

        String accessToken = jwtToken.getAccessToken();
        String refreshToken = jwtToken.getRefreshToken();

        // Base64 인코딩 (필요 시)
        String encodedAccessToken = Base64.getEncoder().encodeToString(accessToken.getBytes());
        String encodedRefreshToken = Base64.getEncoder().encodeToString(refreshToken.getBytes());

        // Access Token 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessTokenCookie", encodedAccessToken);
        accessTokenCookie.setDomain("localhost");
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유효기간
        accessTokenCookie.setSecure(true);
        response.addCookie(accessTokenCookie);

        // Refresh Token 쿠키 설정
        Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", encodedRefreshToken);
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 31); // 한 달 유효기간
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok("로그인 성공");
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

    @PostMapping("/refresh-token")
    public JwtToken refreshAccessToken(@RequestBody JwtDto jwtDto) {
        String refreshToken = jwtDto.refreshToken();
        JwtToken newAccessToken = userServiceImpl.refreshAccessToken(refreshToken);
        return newAccessToken;
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            tokenService.logoutToken(request, response);
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
}
