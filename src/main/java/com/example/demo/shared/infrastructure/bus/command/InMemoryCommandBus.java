package com.example.demo.shared.infrastructure.bus.command;

import com.example.demo.shared.domain.bus.command.Command;
import com.example.demo.shared.domain.bus.command.CommandBus;
import com.example.demo.shared.domain.bus.command.CommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

@Service
public class InMemoryCommandBus implements CommandBus {

    private final ApplicationContext context;

    public InMemoryCommandBus(ApplicationContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void dispatch(Command command) {
        Map<String, CommandHandler> handlers = context.getBeansOfType(CommandHandler.class);

        CommandHandler handler = handlers.values().stream()
                                         .filter(h -> supports(h, command.getClass()))
                                         .findFirst()
                                         .orElseThrow(() -> new IllegalArgumentException("No handler found for command: " + command.getClass().getName()));

        handler.handle(command);
    }

    private boolean supports(CommandHandler handler, Class<? extends Command> commandClass) {
        return java.util.Arrays.stream(handler.getClass().getGenericInterfaces())
                               .filter(type -> type instanceof ParameterizedType)
                               .map(type -> (ParameterizedType) type)
                               .anyMatch(type -> type.getActualTypeArguments()[0].equals(commandClass));
    }
}
