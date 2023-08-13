package com.minglog.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class ScryptPasswordEncoder implements PasswordEncoder{

    private static final SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(32, 8, 1, 32, 64);

    @Override
    public String encrypt(String rawPassword) {
        return sCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches (String rawPassword, String encryptedPassword) {
        return sCryptPasswordEncoder.matches(rawPassword, encryptedPassword);
    }

}
