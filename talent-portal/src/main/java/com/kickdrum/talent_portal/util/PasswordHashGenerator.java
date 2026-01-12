package com.kickdrum.talent_portal.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("basic123  -> " + encoder.encode("basic123"));
        System.out.println("admin123  -> " + encoder.encode("admin123"));
    }
}
