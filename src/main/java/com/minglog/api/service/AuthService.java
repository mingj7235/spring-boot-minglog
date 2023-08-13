package com.minglog.api.service;

import com.minglog.api.domain.User;
import com.minglog.api.exception.AlreadyExistsEmailException;
import com.minglog.api.exception.InvalidSigninInformation;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Login;
import com.minglog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn (Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(32, 8, 1, 32, 64);
        String encryptedPassword = sCryptPasswordEncoder.encode(signup.getPassword());

        User user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
    }
}
