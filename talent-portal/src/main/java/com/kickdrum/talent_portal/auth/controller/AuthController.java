package com.kickdrum.talent_portal.auth.controller;

import com.kickdrum.talent_portal.auth.dto.LoginRequest;
import com.kickdrum.talent_portal.auth.dto.LoginResponse;
import com.kickdrum.talent_portal.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authService.authenticate(request);
        return new LoginResponse(token);
    }
}
