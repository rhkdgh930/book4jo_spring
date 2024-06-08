package com.booksajo.bookPanda.user.service;

import com.booksajo.bookPanda.user.JWT.JwtToken;
import com.booksajo.bookPanda.user.JWT.JwtTokenProvider;
import com.booksajo.bookPanda.user.domain.User;
import com.booksajo.bookPanda.user.repository.UserRepository;
import com.booksajo.bookPanda.user.dto.SignUpDto;
import com.booksajo.bookPanda.user.dto.UserDto;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtToken signIn(String userEmail, String userPassword) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        return jwtToken;
    }

    @Transactional
    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUserEmail(signUpDto.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 아이디입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signUpDto.getUserPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }

    @Transactional
    public void updatePassword(String userEmail, String currentPassword, String newPassword) {
        User user = validatePassword(userEmail, currentPassword);
        user.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User validatePassword(String userEmail, String userPassword) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("로그인 정보가 일치하지 않습니다."));
        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new UsernameNotFoundException("로그인 정보가 일치하지 않습니다.");
        }
        return user;
    }

    public void updateAddress(String userEmail, String newAddress) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("로그인 정보가 일치하지 않습니다."));;
        user.updateAddress(newAddress);
        userRepository.save(user);
    }

    public void updatePhoneNumber(String userEmail, String newPhoneNumber) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("로그인 정보가 일치하지 않습니다."));;
        user.updatePhoneNumber(newPhoneNumber);
        userRepository.save(user);
    }
}
