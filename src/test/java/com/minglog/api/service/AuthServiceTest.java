package com.minglog.api.service;

import com.minglog.api.domain.User;
import com.minglog.api.exception.AlreadyExistsEmailException;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Signup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1 () {
        // given
        Signup signup = Signup.builder()
                .name("ming")
                .password("1234")
                .email("joshuara7235@gmail.com")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("ming", user.getName());
        assertEquals("1234", user.getPassword());
        assertEquals("joshuara7235@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2 () {
        // given
        User registeredUser = User.builder()
                .name("mingming")
                .password("1234")
                .email("joshuara7235@gmail.com")
                .build();

        userRepository.save(registeredUser);

        Signup signup = Signup.builder()
                .name("ming")
                .password("1234")
                .email("joshuara7235@gmail.com")
                .build();

        // when

        Assertions.assertThrows(AlreadyExistsEmailException.class,
                () -> authService.signup(signup));
        // then
    }

}