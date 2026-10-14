package com.example.demo.social_strata.domain.valobj;

import com.example.demo.shared.domain.Identifier;

import java.util.UUID;

public record SocialStrataId(UUID value) implements Identifier<UUID> {
    public static SocialStrataId create(UUID value){
        return new SocialStrataId(value);
    }

    public static SocialStrataId generate(){
        return create(UUID.randomUUID());
    }

    public static SocialStrataId fromString(String value){
        return create(UUID.fromString(value));
    }
}
