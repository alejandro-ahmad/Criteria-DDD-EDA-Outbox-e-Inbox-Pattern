package com.example.demo.shared.infrastructure.address;

import com.example.demo.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class AddressCreatedDomainEvent extends DomainEvent {

    private final String employeeId;


    public AddressCreatedDomainEvent(String addressId, String employeeId) {
        super(addressId);
        this.employeeId = employeeId;
    }

    public AddressCreatedDomainEvent(
            String addressId,
            String eventId,
            String occurredOn,
            String employeeId
    ) {
        super(addressId, eventId, occurredOn);
        this.employeeId = employeeId;
    }


    public static AddressCreatedDomainEvent fromPrimitives(
            String aggregateId,
            Map<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new AddressCreatedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                body.get("employee_id").toString()
        );
    }

    @Override
    public String eventName() {
        return "justucuman.personalapi.1.event.address.created";
    }

    @Override
    public Map<String, Serializable> toPrimitives() {
        Map<String, Serializable> primitives = new HashMap<>();
        primitives.put("employee_id", this.employeeId);
        return primitives;
    }

    public String employeeId() {
        return employeeId;
    }
}
