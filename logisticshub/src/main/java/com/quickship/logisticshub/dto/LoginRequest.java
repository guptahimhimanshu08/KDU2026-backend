package com.quickship.logisticshub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}