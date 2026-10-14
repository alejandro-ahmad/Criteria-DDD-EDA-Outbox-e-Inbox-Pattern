package com.example.demo.social_strata.application.command.update;

import com.example.demo.shared.domain.bus.command.Command;


public record SocialStrataUpdateCommand(
        String id,
        String name
) implements Command {}
