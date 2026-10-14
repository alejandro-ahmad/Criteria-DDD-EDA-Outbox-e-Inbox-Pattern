package com.example.demo.categories.application.exceptions;

import com.example.demo.shared.application.exceptions.AlreadyExistsException;

public class CategoryDuplicateException extends AlreadyExistsException {
    public CategoryDuplicateException(String message) {
        super(message);
    }
}
