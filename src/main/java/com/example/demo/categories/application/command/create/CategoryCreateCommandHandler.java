package com.example.demo.categories.application.command.create;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.command.CommandHandler;

@Service
public class CategoryCreateCommandHandler implements CommandHandler<CategoryCreateCommand> {
    private final CategoryCreator creator;

    public CategoryCreateCommandHandler(CategoryCreator creator){
        this.creator = creator;
    }

    @Override
    public void handle(CategoryCreateCommand command){
        this.creator.execute(command);
    }
}
