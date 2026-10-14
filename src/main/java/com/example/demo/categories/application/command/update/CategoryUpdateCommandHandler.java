package com.example.demo.categories.application.command.update;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.command.CommandHandler;

@Service
public class CategoryUpdateCommandHandler implements CommandHandler<CategoryUpdateCommand> {
    private final CategoryUpdater updater;

    public CategoryUpdateCommandHandler(CategoryUpdater updater){
        this.updater = updater;
    }

    @Override
    public void handle(CategoryUpdateCommand command){
        this.updater.execute(command);
    }
}
