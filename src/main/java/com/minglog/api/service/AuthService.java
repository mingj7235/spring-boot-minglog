package com.minglog.api.service;

import com.minglog.api.domain.User;
import com.minglog.api.exception.InvalidSigninInformation;
import com.minglog.api.repository.UserRepository;
import com.minglog.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
