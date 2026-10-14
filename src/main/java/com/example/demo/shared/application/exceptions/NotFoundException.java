package com.example.demo.shared.application.exceptions;

import com.example.demo.shared.domain.exceptions.DomainException;

public abstract class NotFoundException extends DomainException {
    protected NotFoundException(String message) {
        super(message);
    }
}
