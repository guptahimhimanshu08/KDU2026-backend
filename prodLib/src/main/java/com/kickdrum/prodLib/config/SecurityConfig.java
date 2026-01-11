package com.kickdrum.prodLib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager users() {
        UserDetails librarian = User.withUsername("librarian")
                .password("{noop}password")
                .roles("LIBRARIAN")
                .build();

        UserDetails member = User.withUsername("member")
                .password("{noop}password")
                .roles("MEMBER")
                .build();

        return new InMemoryUserDetailsManager(librarian, member);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

        return http.build();
    }
}
