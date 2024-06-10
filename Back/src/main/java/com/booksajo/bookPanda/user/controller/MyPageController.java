package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.domain.UpdatePasswordRequest;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.repository.UserRepository;
import com.booksajo.bookPanda.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal User user, HttpServletRequest request) {
        String userEmail = user.getUserEmail();
        User userInfo = userRepository.findByUserEmail(userEmail).orElseThrow();
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/{field}")
    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal User user, @PathVariable String field, @RequestBody User updatedUser) {
        String userEmail = user.getUserEmail();
        switch (field) {
            case "userName":
                userServiceImpl.updateName(userEmail, updatedUser.getUsername());
                break;
            case "address":
                userServiceImpl.updateAddress(userEmail, updatedUser.getAddress());
                break;
            case "phoneNumber":
                userServiceImpl.updatePhoneNumber(userEmail, updatedUser.getPhoneNumber());
                break;
            default:
                return ResponseEntity.badRequest().body("필드가 존재하지 않습니다.");
        }
        return ResponseEntity.ok().body("수정 완료");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User user, @Valid @RequestBody UpdatePasswordRequest request) {
        userServiceImpl.updatePassword(user.getUserEmail(), request.getNewPassword());
        return ResponseEntity.ok().body("비밀번호 변경에 성공했습니다.");
    }
}
