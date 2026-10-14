package com.example.demo.social_strata.application.exceptions;

import com.example.demo.shared.application.exceptions.NotFoundException;

public class SocialStrataNotFoundException extends NotFoundException {
    public SocialStrataNotFoundException(String message) {
        super(message);
    }
}
