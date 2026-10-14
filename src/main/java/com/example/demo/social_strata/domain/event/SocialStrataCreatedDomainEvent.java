package com.example.demo.social_strata.domain.event;

import com.example.demo.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class SocialStrataCreatedDomainEvent extends DomainEvent {
    private final String name;

    public SocialStrataCreatedDomainEvent(String aggregateId,String name){
        super(aggregateId);
        this.name = name;
    }

    public SocialStrataCreatedDomainEvent(String aggregateId,String eventId,String occurredOn,String name){
        super(aggregateId,eventId,occurredOn);
        this.name = name;
    }

    @Override
    public String eventName(){
        return "social_strata.created";
    }

    @Override
    public Map<String, Serializable> toPrimitives(){
        Map<String,Serializable> map = new HashMap<>();
        map.put("name",name);
        return map;
    }

    public String name(){
        return name;
    }

}
