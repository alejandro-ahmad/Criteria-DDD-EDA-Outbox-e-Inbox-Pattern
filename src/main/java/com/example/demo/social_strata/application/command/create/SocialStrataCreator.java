package com.example.demo.social_strata.application.command.create;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.event.EventBus;
import com.example.demo.social_strata.application.exceptions.DuplicateSocialStrataException;
import com.example.demo.social_strata.domain.SocialStrataRepository;
import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;

@Service
public final class SocialStrataCreator {
    private final SocialStrataRepository repository;
    private final EventBus eventBus;

    public SocialStrataCreator(SocialStrataRepository repository,EventBus eventBus){
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void create(SocialStrataCreateCommand command){
        SocialStrataName name = SocialStrataName.create(command.name());

        if(repository.existsByName(name)){
            throw new DuplicateSocialStrataException("El turno " + command.name() + " ya existe");
        }

        SocialStrata socialStrata = SocialStrata.create(
                SocialStrataId.generate(),
                name
        );

        repository.save(socialStrata);
        eventBus.publish(socialStrata.pullDomainEvents());
    }
}
