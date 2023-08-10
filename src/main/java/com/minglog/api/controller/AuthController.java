package com.minglog.api.controller;

import com.minglog.api.request.Login;
import com.minglog.api.response.SessionResponse;
import com.minglog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     byte[] encodedKey = key.getEncoded();
     String StrKey = Base64.getEncoder().encodeToString(encodedKey);
     */
    private final String KEY = "y1wG2au2rlAL6qRUX0sbwR7VuCuRYToE7Sv5ih3lyKI=";

    @PostMapping("/auth/login")
    public SessionResponse login (@RequestBody Login login) {
        Long userId = authService.signIn(login);

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }
}
