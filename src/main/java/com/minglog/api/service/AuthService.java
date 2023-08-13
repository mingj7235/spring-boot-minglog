package com.minglog.api.service;

import com.minglog.api.crypto.PasswordEncoder;
import com.minglog.api.crypto.ScryptPasswordEncoder;
import com.minglog.api.domain.User;
import com.minglog.api.exception.AlreadyExistsEmailException;
import com.minglog.api.exception.InvalidSigninInformation;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Login;
import com.minglog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Transactional
    public Long signIn (Login login) {

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();

        if (!encoder.matches(login.getPassword(), user.getPassword())) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

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
