package com.quickship.logisticshub.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.quickship.logisticshub.dto.LoginRequest;
import com.quickship.logisticshub.model.UserEntity;
import com.quickship.logisticshub.repository.UserRepository;
import com.quickship.logisticshub.security.util.JwtUtil;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    

    public AuthService(
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(LoginRequest request) {

        

        // log.info(
        //     "User '{}' logged in successfully with roles {}",
        //     user.getUserName(),
        //     user.getRoles()
        // );
        return jwtUtil.generateToken(
                user.getUserName(),
                user.getRoles()
        );
    }
}
