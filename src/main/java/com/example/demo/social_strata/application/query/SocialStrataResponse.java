package com.example.demo.social_strata.application.query;

import com.example.demo.shared.domain.bus.query.Response;
import com.example.demo.social_strata.domain.model.SocialStrata;

public record SocialStrataResponse(
        String id,
        String name
) implements Response {
    public static SocialStrataResponse fromDomain(SocialStrata socialStrata){
        return new SocialStrataResponse(
                socialStrata.id() != null ? socialStrata.id().value().toString() : null,
                socialStrata.name() != null ? socialStrata.name().value() : null
        );
    }
}
