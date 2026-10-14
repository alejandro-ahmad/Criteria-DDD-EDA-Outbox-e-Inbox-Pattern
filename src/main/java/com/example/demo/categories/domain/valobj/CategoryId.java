package com.example.demo.categories.domain.valobj;

import com.example.demo.shared.domain.Identifier;

import java.util.UUID;

public record CategoryId(UUID value) implements Identifier<UUID> {
    public static CategoryId create(UUID value){
        if(value == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return new CategoryId(value);
    }

    public static CategoryId generate(){
        return create(UUID.randomUUID());
    }

    public static CategoryId fromString(String value){
        return create(UUID.fromString(value));
    }
}
