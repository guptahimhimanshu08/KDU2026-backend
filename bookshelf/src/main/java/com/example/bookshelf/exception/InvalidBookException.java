package com.example.bookshelf.exception;

public class InvalidBookException extends RuntimeException {

    public InvalidBookException(String message) {
        super(message);
    }

    public InvalidBookException(String message, Throwable cause) {
        super(message, cause);
    }
}