package com.example.demo.shared.domain.bus.command;

import com.example.demo.shared.domain.Service;

@Service
public interface CommandBus {
    void dispatch(Command command) throws CommandHandlerExecutionError;
}
