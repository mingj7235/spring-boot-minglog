package com.minglog.api.controller;

import com.minglog.api.request.Login;
import com.minglog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login (@RequestBody Login login) {
        String accessToken = authService.signIn(login);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        log.info(">>>>>>>>>>>cookie = {} ", cookie);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
