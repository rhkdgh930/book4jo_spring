package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.JWT.JwtAuthenticationFilter;
import com.booksajo.bookPanda.user.JWT.JwtToken;
import com.booksajo.bookPanda.user.domain.UpdatePasswordRequest;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtDto> signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
        String userEmail = signInDto.getUserEmail();
        String userPassword = signInDto.getUserPassword();

        // 사용자 조회
        User user = userRepository.findByUserEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        JwtToken jwtToken = userServiceImpl.signIn(userEmail, userPassword);

        String accessToken = jwtToken.getAccessToken();
        String refreshToken = jwtToken.getRefreshToken();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

        // 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유효기간
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 유효기간
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(new JwtDto(jwtToken.getAccessToken(),
            jwtToken.getRefreshToken()));
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

    @PostMapping("/change-password")
    public String changePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userServiceImpl.updatePassword(request.getUserEmail(), request.getNewPassword());
        return "비밀번호 변경에 성공했습니다.";
    }
}
