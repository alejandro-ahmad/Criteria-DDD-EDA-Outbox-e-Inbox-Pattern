package com.example.demo.social_strata.application.command.delete;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.command.CommandHandler;

@Service
public final class SocialStrataDeleteCommandHandler implements CommandHandler<SocialStrataDeleteCommand> {
    private final SocialStrataDeleter deleter;

    public SocialStrataDeleteCommandHandler(SocialStrataDeleter deleter){
        this.deleter = deleter;
    }

    @Override
    public void handle(SocialStrataDeleteCommand command){
        this.deleter.execute(command);
    }
}
