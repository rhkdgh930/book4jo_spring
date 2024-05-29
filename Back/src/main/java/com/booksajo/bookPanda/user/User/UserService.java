package com.booksajo.bookPanda.user.User;

import com.booksajo.bookPanda.user.JWT.SecurityUtil;
import com.booksajo.bookPanda.user.JWT.Authority;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

        // 유저 정보를 만들어서 save
        User user = User.builder()
            .userEmail(userDto.getUserEmail())
            .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
            .userName(userDto.getUserName())
            .authorities(Collections.singleton(authority))
            .activated(true)
            .build();

        return userRepository.save(user);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String userEmail) {
        return userRepository.findOneWithAuthoritiesByUserEmail(userEmail);
    }

    // 현재 securityContext에 저장된 userId의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUserEmail()
            .flatMap(userRepository::findOneWithAuthoritiesByUserEmail);
    }
}