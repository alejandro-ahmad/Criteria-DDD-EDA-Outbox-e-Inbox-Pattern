package com.example.demo.categories.domain.valobj;

import com.example.demo.shared.domain.Identifier;

public record CategoryName(String value) implements Identifier<String> {
    public static CategoryName create(String value){
        return new CategoryName(value.toUpperCase());
    }

    public static CategoryName fromPersistence(String value){
        return create(value);
    }
}
