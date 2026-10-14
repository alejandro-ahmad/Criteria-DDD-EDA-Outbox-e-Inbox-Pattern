package com.example.demo.shared.domain.exceptions;

public class BusinessLogicException extends InvalidArgumentException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
