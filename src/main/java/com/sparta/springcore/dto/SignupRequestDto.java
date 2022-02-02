package com.sparta.springcore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String password2;

    @NotBlank(message="이메일을 입력해 주세요.")
    @Email(message="이메일 형식에 맞지 않습니다.")
    private String email;


    @Builder
    public SignupRequestDto(String username, String password,String email, String password2) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.password2 = password2;
    }

}
