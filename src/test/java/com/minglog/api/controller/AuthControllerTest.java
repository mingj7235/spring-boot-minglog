package com.minglog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minglog.api.domain.Session;
import com.minglog.api.domain.User;
import com.minglog.api.repository.SessionRepository;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Login;
import com.minglog.api.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc //mockMvc 를 주입받기 위해
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test1() throws Exception {
        // given
        userRepository.save(User.builder()
                        .name("밍밍")
                        .email("ming7235@gmail.com")
                        .password("1234")
                .build());

        Login login = Login.builder()
                .email("ming7235@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void test2() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("밍밍")
                .email("ming7235@gmail.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("ming7235@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // when
//        mockMvc.perform(post("/auth/login")
//                        .contentType(APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        assertEquals(1L, user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다 /foo")
    void test4() throws Exception {
        // given

        User user = User.builder()
                .name("밍밍")
                .email("ming7235@gmail.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        // when
//        mockMvc.perform(get("/foo")
//                        .header("Authorization", session.getAccessToken())
//                        .contentType(APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 검증되지 않은 세션 값으로 권한이 필요한 페이지에 접속할 수 없다. ")
    void test5() throws Exception {
        // given

        User user = User.builder()
                .name("밍밍")
                .email("ming7235@gmail.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        // when
        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken() + "_other")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입")
    void test6() throws Exception {
        // given
        Signup signup = Signup.builder()
                .name("ming")
                .password("1234")
                .email("joshuara7235@gmail.com")
                .build();

        // when
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}