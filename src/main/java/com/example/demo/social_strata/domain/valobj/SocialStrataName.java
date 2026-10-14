package com.example.demo.social_strata.domain.valobj;

import com.example.demo.shared.domain.Identifier;

public record SocialStrataName(String value) implements Identifier<String> {
    public static SocialStrataName create(String value){
        return new SocialStrataName(value.toUpperCase());
    }

    public static SocialStrataName fromPersistence(String value){
        return create(value);
    }
}
