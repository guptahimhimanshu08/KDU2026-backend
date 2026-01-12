package com.quickship.logisticshub.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'DRIVER')")
    public String viewUsers() {
        return "User list visible";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser() {
        return "User added";
    }
}