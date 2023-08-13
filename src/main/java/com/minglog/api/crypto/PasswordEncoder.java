package com.minglog.api.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(32, 8, 1, 32, 64);

    public String encrypt(String rawPassword) {
        return sCryptPasswordEncoder.encode(rawPassword);
    }

    public boolean matches (String rawPassword, String encryptedPassword) {
        return sCryptPasswordEncoder.matches(rawPassword, encryptedPassword);
    }

}
