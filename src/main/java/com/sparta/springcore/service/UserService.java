package com.sparta.springcore.service;

import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.transaction.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(SignupRequestDto requestDto ) throws Exception {
        // 회원 ID 중복 및 유효성 검사
        String username = chekUsername(requestDto);

        //패스워드 유효성 검사
        checkPassword(requestDto);

        // 패스워드 암호화
        String encodepassword = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        User user = new User(username, encodepassword, email);
        userRepository.save(user);

    }

    public void checkPassword(SignupRequestDto requestDto)  {
        if(requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new IllegalArgumentException("패스워드에 유저네임이 들어갈 수 없습니다");
        }

        if(!requestDto.getPassword().equals(requestDto.getPassword2())) {
            throw new IllegalArgumentException("비밀번호를 일치 시켜주세요");
        }

        int length = requestDto.getPassword().length();

        if (length <4 || length >12) {
            throw new IllegalArgumentException("패스워드는 4~12 자리로 만들어주세요.");
        }


    }

    public String chekUsername(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        int length = username.length();
        if (length <3 || length >15) {
            throw new IllegalArgumentException("닉네임은 3~15 자리로 만들어주세요");
        }

        if(!username.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("닉네임은 영문 및 숫자가 1개씩 들어가야 합니다.");
        }

        if(!username.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("닉네임은 영문 및 숫자가 1개씩 들어가야 합니다.");
        }

        return username;
    }

    // Controller에서 유효성 검사에 실패한 필드가 있다면, Service 계층으로 Errors 객체를 전달하여 비즈니스 로직을 구현합니다.
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

}



