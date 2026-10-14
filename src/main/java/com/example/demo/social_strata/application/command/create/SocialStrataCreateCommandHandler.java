package com.example.demo.social_strata.application.command.create;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.command.CommandHandler;


@Service
public final class SocialStrataCreateCommandHandler implements CommandHandler<SocialStrataCreateCommand> {
    private final SocialStrataCreator creator;

    public SocialStrataCreateCommandHandler(SocialStrataCreator creator){
        this.creator = creator;
    }

    @Override
    public void handle(SocialStrataCreateCommand command){
        this.creator.create(command);
    }
}
