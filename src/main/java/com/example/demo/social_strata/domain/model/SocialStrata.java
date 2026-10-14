package com.example.demo.social_strata.domain.model;

import com.example.demo.shared.domain.AggregateRoot;
import com.example.demo.social_strata.domain.event.SocialStrataCreatedDomainEvent;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;

public class SocialStrata extends AggregateRoot {
    private final SocialStrataId   id;
    private       SocialStrataName name;

    public SocialStrata(SocialStrataId id, SocialStrataName name){
        this.id = id;
        this.name = name;
    }

    public static SocialStrata create(SocialStrataId id,SocialStrataName name){
        SocialStrata socialStrata = new SocialStrata(id,name);

        socialStrata.record(new SocialStrataCreatedDomainEvent(id.value().toString(),name.value()));

        return socialStrata;
    }

    public void updateName(SocialStrataName name) {
        this.name = name;
    }

    public SocialStrataId id(){
        return id;
    }

    public SocialStrataName name(){
        return name;
    }
}
