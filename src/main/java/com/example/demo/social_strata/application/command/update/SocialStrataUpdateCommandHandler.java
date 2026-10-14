package com.example.demo.social_strata.application.command.update;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.command.CommandHandler;
import com.example.demo.social_strata.application.command.create.SocialStrataCreateCommand;
import com.example.demo.social_strata.application.command.create.SocialStrataCreator;

@Service
public final class SocialStrataUpdateCommandHandler implements CommandHandler<SocialStrataCreateCommand> {
    private final SocialStrataCreator creator;

    public SocialStrataUpdateCommandHandler(SocialStrataCreator creator){
        this.creator = creator;
    }

    @Override
    public void handle(SocialStrataCreateCommand command){
        this.creator.create(command);
    }
}
