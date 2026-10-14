package com.example.demo.social_strata.infrastructure.input.put;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SocialStrataPutRequest(
        @NotNull(message = "El nombre es obligatorio.")
        @Pattern(
                regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$",
                message = "El nombre solo puede contener letras y un único espacio entre palabras"
        )
        @Size(
                max = 100,
                message = "El nombre no puede superar los 100 caracteres"
        )
        @Schema(example = "MAGISTRADO")
        String name
) {}
