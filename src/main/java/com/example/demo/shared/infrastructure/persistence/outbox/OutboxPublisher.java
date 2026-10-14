package com.example.demo.shared.infrastructure.persistence.outbox;

import com.example.demo.shared.domain.bus.event.DomainEvent;
import com.example.demo.shared.domain.bus.event.EventBus;
import com.example.demo.shared.infrastructure.bus.event.DomainEventJsonDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxPublisher {

    private static final int BATCH_SIZE = 50;

    private final JpaOutboxRepository outboxRepository;
    private final DomainEventJsonDeserializer deserializer;
    private final EventBus springEventBus;

    public OutboxPublisher(
            JpaOutboxRepository outboxRepository,
            DomainEventJsonDeserializer deserializer,
            @Qualifier("springApplicationEventBus") EventBus springEventBus
    ) {
        this.outboxRepository = outboxRepository;
        this.deserializer = deserializer;
        this.springEventBus = springEventBus;
    }

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEventEntity> pendingEvents = outboxRepository.findPendingForProcessing(BATCH_SIZE);

        if (pendingEvents.isEmpty()) {
            return;
        }

        for (OutboxEventEntity outboxEvent : pendingEvents) {
            try {
                DomainEvent domainEvent = deserializer.deserialize(
                        outboxEvent.getEventType(),
                        outboxEvent.getPayload()
                );

                springEventBus.publish(List.of(domainEvent));

                outboxEvent.markAsCompleted();
            } catch (Exception e) {
                outboxEvent.markAsFailed(e.getMessage());
            }
        }

        outboxRepository.saveAll(pendingEvents);
    }
}
