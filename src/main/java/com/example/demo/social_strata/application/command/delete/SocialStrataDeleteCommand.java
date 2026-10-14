package com.example.demo.social_strata.application.command.delete;

import com.example.demo.shared.domain.bus.command.Command;

import java.util.UUID;

public record SocialStrataDeleteCommand(UUID id) implements Command {}
