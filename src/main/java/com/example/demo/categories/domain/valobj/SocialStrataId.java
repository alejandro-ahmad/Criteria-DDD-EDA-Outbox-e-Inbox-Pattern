package com.example.demo.categories.domain.valobj;

import com.example.demo.shared.domain.Identifier;

public record SocialStrataId(String value) implements Identifier<String> {
    public static SocialStrataId create(String value){
        return new SocialStrataId(value);
    }

    public static SocialStrataId fromPersistence(String value){
        return create(value);
    }
}
