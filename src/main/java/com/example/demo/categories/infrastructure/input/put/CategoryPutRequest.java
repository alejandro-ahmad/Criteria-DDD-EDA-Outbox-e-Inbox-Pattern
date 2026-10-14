package com.example.demo.categories.infrastructure.input.put;

import jakarta.validation.constraints.NotNull;

public record CategoryPutRequest(
        @NotNull(message = "El nombre no puede ser nulo.")
        String name,
        String carnetName,
        @NotNull(message = "El estamento no puede ser nulo.")
        String socialStrataId
) {}
