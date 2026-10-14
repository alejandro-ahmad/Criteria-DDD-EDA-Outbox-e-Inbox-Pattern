package com.example.demo.social_strata.application.command.update;

import com.example.demo.shared.domain.Service;
import com.example.demo.social_strata.application.exceptions.DuplicateSocialStrataException;
import com.example.demo.social_strata.application.exceptions.SocialStrataNotFoundException;
import com.example.demo.social_strata.domain.SocialStrataRepository;
import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;

@Service
public final class SocialStrataUpdater {
    private final SocialStrataRepository repository;

    public SocialStrataUpdater(SocialStrataRepository repository){
        this.repository = repository;
    }

    public void execute(SocialStrataUpdateCommand command){
        SocialStrataName name = SocialStrataName.create(command.name());
        SocialStrataId   id   = SocialStrataId.fromString(command.id());

        SocialStrata socialStrata = repository.findById(id).orElseThrow(() -> new SocialStrataNotFoundException(
                "No existe un turno con id: " + command.id()
        ));

        if(repository.existsByNameAndIdNot(name,id)){
            throw new DuplicateSocialStrataException("Ya existe un turno: " + name.value());
        }

        socialStrata.updateName(name);
        repository.save(socialStrata);
    }
}
