package com.quickship.logisticshub.exception;

public class PackageAlreadyExistsException extends RuntimeException {
    public PackageAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
