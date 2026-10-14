package com.example.demo.categories.application.query;

import com.example.demo.categories.domain.model.Category;
import com.example.demo.shared.domain.bus.query.Response;

public record CategoryResponse(
        String id,
        String name,
        String carnetName,
        String socialStrataId,
        String socialStrataName,
        Integer code
) implements Response {}
