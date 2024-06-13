package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class test {

    private final UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserDetails user) {

        System.out.println(user.getAuthorities());
        User user1 = userRepository.findByUserEmail(user.getUsername()).orElseThrow();
        System.out.println(user1.getAuthorities());

        return ResponseEntity.ok(null);
    }
}
