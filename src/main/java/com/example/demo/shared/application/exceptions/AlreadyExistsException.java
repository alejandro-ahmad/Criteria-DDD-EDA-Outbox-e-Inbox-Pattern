package com.example.demo.shared.application.exceptions;

public abstract class AlreadyExistsException extends RuntimeException {
    protected AlreadyExistsException(String message) {
        super(message);
    }
}
