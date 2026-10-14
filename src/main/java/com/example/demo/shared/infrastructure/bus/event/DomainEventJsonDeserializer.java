package com.example.demo.shared.infrastructure.bus.event;

import com.example.demo.shared.domain.bus.event.DomainEvent;
import com.example.demo.social_strata.domain.event.SocialStrataCreatedDomainEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DomainEventJsonDeserializer {
    private final ObjectMapper objectMapper;
    private final Map<String,Class<? extends DomainEvent>> eventMapping = new HashMap<>();

    public DomainEventJsonDeserializer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        register("social_strata.created", SocialStrataCreatedDomainEvent.class);
    }

    public void register(String eventName, Class<? extends DomainEvent> eventClass){
        eventMapping.put(eventName,eventClass);
    }

    public DomainEvent deserialize(String eventType,String payload){
        Class<? extends DomainEvent> eventClass = eventMapping.get(eventType);

        if(eventClass == null){
            throw new IllegalArgumentException("No hay clase registrada para el evento.");
        }

        try{
            Map<String,Object> data = objectMapper.readValue(payload, new TypeReference<>() {});

            String aggregateId = (String) data.get("aggregate_id");
            String eventId = (String) data.get("event_id");
            String occurredOn = (String) data.get("occurred_on");

            if(eventClass.equals(SocialStrataCreatedDomainEvent.class)){
                String name = (String) data.get("name");
                return new SocialStrataCreatedDomainEvent(aggregateId,eventId,occurredOn,name);
            }
            throw new UnsupportedOperationException("Falta implementar la deserialización de: " + eventClass.getSimpleName());
        }catch (Exception e) {
            throw new RuntimeException("Error al deserializar evento de tipo " + eventType, e);
        }
    }
}
