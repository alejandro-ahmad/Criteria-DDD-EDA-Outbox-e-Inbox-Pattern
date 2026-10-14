package com.example.demo.categories.domain.valobj;

import com.example.demo.shared.domain.Identifier;

public record CategoryCarnetName(String value) implements Identifier<String> {
    public static CategoryCarnetName create(String value){
        if(value == null){
            return null;
        }
        return new CategoryCarnetName(value.toUpperCase());
    }

    public static CategoryCarnetName fromPersistence(String value){
        return create(value);
    }
}
