package com.example.demo.categories.application.command.update;

import com.example.demo.shared.domain.bus.command.Command;

public record CategoryUpdateCommand(
        String id,
        String socialStrataId,
        String name,
        String carnetName
) implements Command {}
