package com.example.demo.shared.domain;

import com.example.demo.shared.domain.bus.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public final List<DomainEvent> pullDomainEvents(){
        List<DomainEvent> events = new ArrayList<>(domainEvents);

        domainEvents.clear();

        return events;
    }

    protected final void record(DomainEvent event){
        domainEvents.add(event);
    }
}
