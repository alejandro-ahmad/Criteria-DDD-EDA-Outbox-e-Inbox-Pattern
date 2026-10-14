package com.example.demo.categories.application.exceptions;

import com.example.demo.shared.application.exceptions.NotFoundException;

public class SocialStrataNotExistsException extends NotFoundException {
    public SocialStrataNotExistsException(String message) {
        super(message);
    }
}
