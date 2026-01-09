package com.kickdrum.talent_portal.auth.service;

import com.kickdrum.talent_portal.auth.dto.LoginRequest;
import com.kickdrum.talent_portal.security.util.JwtUtil;
import com.kickdrum.talent_portal.user.entity.UserEntity;
import com.kickdrum.talent_portal.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(LoginRequest request) {

        UserEntity user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        log.info(
            "User '{}' logged in successfully with roles {}",
            user.getUserName(),
            user.getRoles()
        );
        return jwtUtil.generateToken(
                user.getUserName(),
                user.getRoles()
        );
    }
}
