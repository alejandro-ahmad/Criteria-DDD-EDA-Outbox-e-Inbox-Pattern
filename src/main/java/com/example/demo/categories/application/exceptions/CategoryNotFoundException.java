package com.example.demo.categories.application.exceptions;

import com.example.demo.shared.application.exceptions.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
