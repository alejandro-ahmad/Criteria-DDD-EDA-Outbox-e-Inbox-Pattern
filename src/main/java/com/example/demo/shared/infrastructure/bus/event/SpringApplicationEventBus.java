package com.example.demo.shared.infrastructure.bus.event;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.event.DomainEvent;
import com.example.demo.shared.domain.bus.event.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
@Qualifier(value = "springApplicationEventBus")
public final class SpringApplicationEventBus implements EventBus {

    private final ApplicationEventPublisher publisher;

    public SpringApplicationEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(final List<DomainEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

        events.forEach(publisher::publishEvent);
    }
}
