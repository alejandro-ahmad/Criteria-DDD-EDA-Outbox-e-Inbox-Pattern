package com.example.demo.social_strata.application.command.delete;

import com.example.demo.shared.domain.Service;
import com.example.demo.social_strata.application.exceptions.SocialStrataNotFoundException;
import com.example.demo.social_strata.domain.SocialStrataRepository;
import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;

@Service
public final class SocialStrataDeleter {
    private final SocialStrataRepository repository;

    public SocialStrataDeleter(SocialStrataRepository repository){
        this.repository = repository;
    }

    public void execute(SocialStrataDeleteCommand command){
        SocialStrataId socialStrataId = new SocialStrataId(command.id());

        SocialStrata socialStrata = repository.findById(socialStrataId).orElseThrow(() -> new SocialStrataNotFoundException(
                "No existe un turno con id: " + command.id()
        ));

        repository.delete(socialStrata.id());
    }
}
