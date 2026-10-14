package com.example.demo.categories.infrastructure.input.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CategoryPostRequest(
        @NotNull(message = "El nombre no puede ser nulo.")
        @Schema(example = "JUEZ DE PAZ")
        String name,

        @Schema(example = "Funcionario/a")
        String carnetName,

        @NotNull(message = "El nombre no puede ser nulo.")
        String socialStrataId,

        @NotNull(message = "El nombre no puede ser nulo.")
        Integer code

) {}
