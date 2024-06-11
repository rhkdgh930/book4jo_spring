package com.booksajo.bookPanda.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserDetails user) {

        System.out.println(user.getPassword());

        return ResponseEntity.ok(null);
    }
}
