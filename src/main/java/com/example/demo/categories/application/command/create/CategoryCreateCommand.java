package com.example.demo.categories.application.command.create;

import com.example.demo.shared.domain.bus.command.Command;

public record CategoryCreateCommand(
        String socialStrataId,
        String name,
        String carnetName,
        Integer code
) implements Command {}
