package com.booksajo.bookPanda.user.controller;

import com.booksajo.bookPanda.user.dto.VerifyCodeDto;
import com.booksajo.bookPanda.user.service.MailService;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/sign-up")
public class MailController {

    private final MailService mailService;

    // 인증 이메일 전송
    @PostMapping("/sendmail")
    public String mailConfirm(HttpSession httpSession, @RequestParam(value = "email", required = false) String email)
        throws Exception {
        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        httpSession.setAttribute("code", code); //쿠키에 저장할수도있음
        return code;
    }

    // 검증
    @PostMapping("/verify-code")
    public String verifyCode(HttpSession httpSession, @RequestBody VerifyCodeDto verifyCodeDto) {
        boolean result = false;
        if (httpSession.getAttribute("code").equals(verifyCodeDto.getVerifyCode())) {
            result = true;
        } else {
            throw new NoSuchElementException("인증번호가 일치하지 않습니다.");
        }
        return String.valueOf(result);
    }
}
