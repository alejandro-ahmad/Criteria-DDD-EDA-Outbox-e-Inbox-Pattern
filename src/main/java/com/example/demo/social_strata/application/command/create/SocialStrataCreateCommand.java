package com.example.demo.social_strata.application.command.create;

import com.example.demo.shared.domain.bus.command.Command;

public record SocialStrataCreateCommand(String name) implements Command {}
