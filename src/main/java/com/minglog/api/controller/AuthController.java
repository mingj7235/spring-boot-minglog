package com.minglog.api.controller;

import com.minglog.api.request.Login;
import com.minglog.api.response.SessionResponse;
import com.minglog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login (@RequestBody Login login) {
        return new SessionResponse(authService.signIn(login));
    }
}
