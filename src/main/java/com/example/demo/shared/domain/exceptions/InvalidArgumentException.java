package com.example.demo.shared.domain.exceptions;

public abstract class InvalidArgumentException extends DomainException {
    protected InvalidArgumentException(String message) {
        super(message);
    }
}
