package com.quickship.logisticshub.config;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.quickship.logisticshub.security.filter.JwtAuthenticationFilter;
import com.quickship.logisticshub.security.handler.CustomAccessDeniedHandler;
import com.quickship.logisticshub.security.handler.CustomAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, 
                        CustomAccessDeniedHandler accessDeniedHandler,
                        CustomAuthenticationEntryPoint customAuthEntryPoint   
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customAuthEntryPoint = customAuthEntryPoint;
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails manager = User.withUsername("manager")
                .password("{noop}password")
                .roles("MANAGER")
                .build();

        UserDetails driver = User.withUsername("driver")
                .password("{noop}password")
                .roles("DRIVER")
                .build();

        return new InMemoryUserDetailsManager(manager, driver);
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).authorizeHttpRequests(auth -> auth

                .requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .exceptionHandling(exception -> 
                exception
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(customAuthEntryPoint)
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}