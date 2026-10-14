package com.example.demo.shared.infrastructure.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorDetail", description = "An error detail description")
public record ErrorDetail(String message) {}
