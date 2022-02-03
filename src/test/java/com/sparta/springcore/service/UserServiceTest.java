package com.sparta.springcore.service;


import com.sparta.springcore.dto.SignupRequestDto;

import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    static String username;
    static String password;
    static String password2;
    static String email;

    @Nested
    @DisplayName("테스트시작")
    class productCheck {

        @Test
        @DisplayName("정상케이스")
        void test1() {

            //when
            SignupRequestDto signupRequestDto = new SignupRequestDto(
                    username = "abc",
                    password = "1234",
                    email = "1234@naver.com",
                    password2 = "1234"
            );
            //then
            assertEquals(username, signupRequestDto.getUsername());
            assertEquals(password, signupRequestDto.getPassword());
            assertEquals(email, signupRequestDto.getEmail());
            assertEquals(password2, signupRequestDto.getPassword2());

        }

        @Nested
        @DisplayName("실패 케이스")
        class productCheckFail {

            @Test
            @DisplayName("비밀번호 안에 닉네임1")
            void test2() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "1234",
                        password = "1234",
                        email = "1234@naver.com",
                        password2 = "1234"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.checkPassword(signupRequestDto);
                });

                //then
                assertEquals("패스워드에 유저네임이 들어갈 수 없습니다", exception.getMessage());

            }

            @Test
            @DisplayName("비밀번호 안에 닉네임2")
            void test3() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "abcd",
                        password = "abcd",
                        email = "1234@naver.com",
                        password2 = "abcd"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.checkPassword(signupRequestDto);
                });

                //then
                assertEquals("패스워드에 유저네임이 들어갈 수 없습니다", exception.getMessage());

            }

            @Test
            @DisplayName("비밀번호 체크1")
            void test4() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "abcde",
                        password = "12345",
                        email = "1234@naver.com",
                        password2 = "1234"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.checkPassword(signupRequestDto);
                });

                //then
                assertEquals("비밀번호를 일치 시켜주세요", exception.getMessage());

            }

            @Test
            @DisplayName("비밀번호 체크2")
            void test5() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "abcde",
                        password = "2345",
                        email = "1234@naver.com",
                        password2 = "1234"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.checkPassword(signupRequestDto);
                });

                //then
                assertEquals("비밀번호를 일치 시켜주세요", exception.getMessage());

            }





            @Test
            @DisplayName("아이디 중복 체크1")
            void test6() {

                Long kakaoId;

                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "abc123",
                        password = "123334",
                        email = "1234@naver.com",
                        password2 = "123334"
                );

                User user = new User (
                        username = "abc123",
                        password = "12345",
                        email = "1234@naver.com",
                        kakaoId= 3L
                );

                UserService userService = new UserService(userRepository, passwordEncoder);
                when(userRepository.findByUsername(signupRequestDto.getUsername())).thenReturn(Optional.of(user));

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.chekUsername(signupRequestDto);
                });

                //then
                assertEquals("중복된 사용자 ID 가 존재합니다.", exception.getMessage());

            }

            @Test
            @DisplayName("아이디 중복 체크2")
            void test7() {

                Long kakaoId;

                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "aaaa123",
                        password = "1345334",
                        email = "1234@naver.com",
                        password2 = "1345334"
                );

                User user = new User (
                        username = "aaaa123",
                        password = "112455",
                        email = "1234@naver.com",
                        kakaoId= 3L
                );

                UserService userService = new UserService(userRepository, passwordEncoder);
                when(userRepository.findByUsername(signupRequestDto.getUsername())).thenReturn(Optional.of(user));

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.chekUsername(signupRequestDto);
                });

                //then
                assertEquals("중복된 사용자 ID 가 존재합니다.", exception.getMessage());

            }

            @Test
            @DisplayName("닉네임 최소 3자 이상")
            void test8() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "a1",
                        password = "1234",
                        email = "1234@naver.com",
                        password2 = "1234"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.chekUsername(signupRequestDto);
                });

                //then
                assertEquals("닉네임은 3~15 자리로 만들어주세요", exception.getMessage());

            }

            @Test
            @DisplayName("닉네임 영문 및 숫자 1개이상 포함")
            void test9() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "aba",
                        password = "1234",
                        email = "1234@naver.com",
                        password2 = "1234"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.chekUsername(signupRequestDto);
                });

                //then
                assertEquals("닉네임은 영문 및 숫자가 1개씩 들어가야 합니다.", exception.getMessage());

            }

            @Test
            @DisplayName("비밀 번호 최소 4자 이상")
            void test10() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(
                        username = "aba1213",
                        password = "123",
                        email = "1234@naver.com",
                        password2 = "123"
                );

                UserService userService = new UserService(userRepository, passwordEncoder);

                //when
                Exception exception = assertThrows(IllegalArgumentException.class,()-> {
                    userService.checkPassword(signupRequestDto);
                });

                //then
                assertEquals("패스워드는 4~12 자리로 만들어주세요.", exception.getMessage());

            }


        }
    }


}