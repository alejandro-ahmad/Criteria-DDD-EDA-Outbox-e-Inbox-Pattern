package com.example.demo.shared.infrastructure.bus.event;

import com.example.demo.shared.domain.bus.event.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class DomainEventJsonSerializer {
    private final ObjectMapper objectMapper;

    public DomainEventJsonSerializer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public String serialize(DomainEvent event){
        try{
            Map<String, Serializable> attributes = new HashMap<>(event.toPrimitives());
            attributes.put("aggregate_id",event.aggregateId());
            attributes.put("event_id",event.eventId());
            attributes.put("occurred_on",event.occurredOn());
            return objectMapper.writeValueAsString(attributes);
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error al serializar el evento de dominio a JSON");
        }
    }
}
