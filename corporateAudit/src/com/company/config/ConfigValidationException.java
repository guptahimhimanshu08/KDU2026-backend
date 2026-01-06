package com.company.config;

public class ConfigValidationException extends RuntimeException {

    public ConfigValidationException(String m) {
        super(m);
    }
}