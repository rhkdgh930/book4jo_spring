package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final UserServiceImpl userServiceImpl;
    //newPassword 받는법 정하고 수정해야함.
    @PatchMapping("/update-password")
    public void updatePassword(@AuthenticationPrincipal User user, String newPassword) {
        userServiceImpl.updatePassword(user.getUserEmail(), user.getPassword(), newPassword);
    }

    @PatchMapping("/update-address")
    public void updateAddress(@AuthenticationPrincipal User user, String newAddress) {
        userServiceImpl.updateAddress(user.getUserEmail(), newAddress);
    }

    @PatchMapping("/update-phone-number")
    public void updatePhoneNumber(@AuthenticationPrincipal User user, String newPhoneNumber) {
        userServiceImpl.updatePhoneNumber(user.getUserEmail(), newPhoneNumber);
    }
}
