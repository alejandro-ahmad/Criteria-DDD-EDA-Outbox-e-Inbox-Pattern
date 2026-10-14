package com.example.demo.shared.infrastructure.bus.event;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.event.DomainEvent;
import com.example.demo.shared.domain.bus.event.EventBus;
import com.example.demo.shared.infrastructure.persistence.outbox.JpaOutboxRepository;
import com.example.demo.shared.infrastructure.persistence.outbox.OutboxEventEntity;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Service
@Primary
public class PostgreSqlOutboxEventBus implements EventBus {
    private final JpaOutboxRepository repository;
    private final DomainEventJsonSerializer serializer;

    public PostgreSqlOutboxEventBus(JpaOutboxRepository repository, DomainEventJsonSerializer serializer){
        this.repository = repository;
        this.serializer = serializer;
    }

    @Override
    public void publish(final List<DomainEvent> events){
        if(events == null || events.isEmpty()){
            return;
        }


        // OPTÉ POR UN BLOQUE DECLARATIVO UTILIZANDO MAP EN LUGAR DE UN FOR PORQUE DE ESTA MANERA PUEDO USAR
        // repository.saveAll(entities), de esta manera le permite a spring data JPA e hibernate poder guardar
        // por lotes (batching) y no abrir y cerrar llamadas individuales mediante un for
        List<OutboxEventEntity> entities = events.stream()
                .map(event -> new OutboxEventEntity(
                        event.eventId(),
                        event.aggregateId(),
                        event.eventName(), serializer.serialize(event)
                )).toList();

        repository.saveAll(entities);
    }
}
