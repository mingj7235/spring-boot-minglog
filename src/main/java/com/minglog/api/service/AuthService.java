package com.minglog.api.service;

import com.minglog.api.crypto.PasswordEncoder;
import com.minglog.api.domain.User;
import com.minglog.api.exception.AlreadyExistsEmailException;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = encoder.encrypt(signup.getPassword());

        User user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
    }
}
