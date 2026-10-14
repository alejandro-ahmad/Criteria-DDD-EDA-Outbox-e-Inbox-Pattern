package com.example.demo.social_strata.application.exceptions;

import com.example.demo.shared.application.exceptions.AlreadyExistsException;

public class DuplicateSocialStrataException extends AlreadyExistsException {
    public DuplicateSocialStrataException(String message) {
        super(message);
    }
}
