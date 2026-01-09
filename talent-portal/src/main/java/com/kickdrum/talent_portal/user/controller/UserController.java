package com.kickdrum.talent_portal.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    @PreAuthorize("hasAnyRole('BASIC', 'ADMIN')")
    public String viewUsers() {
        return "User list visible";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser() {
        return "User added";
    }
}
