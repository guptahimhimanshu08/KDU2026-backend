package com.quickship.logisticshub.exception;

public class InvalidPackageException extends RuntimeException {

    public InvalidPackageException(String message) {
        super(message);
    }

    public InvalidPackageException(String message, Throwable cause) {
        super(message, cause);
    }
}
