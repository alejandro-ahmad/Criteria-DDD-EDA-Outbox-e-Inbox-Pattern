package com.example.demo.social_strata.infrastructure.exceptions;

import com.example.demo.shared.application.exceptions.NotFoundException;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
