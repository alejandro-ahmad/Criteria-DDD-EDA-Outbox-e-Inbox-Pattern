package com.example.demo.social_strata.application.query.find_by_id;

import com.example.demo.shared.domain.Service;
import com.example.demo.social_strata.application.exceptions.SocialStrataNotFoundException;
import com.example.demo.social_strata.application.query.SocialStrataResponse;
import com.example.demo.social_strata.domain.SocialStrataRepository;
import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;

@Service
public final class SocialStrataFinderById {
    private final SocialStrataRepository repository;

    public SocialStrataFinderById(SocialStrataRepository repository){
        this.repository = repository;
    }

    public SocialStrataResponse find(String id) {
        SocialStrata socialStrata = repository.findById(SocialStrataId.fromString(id)).orElseThrow(() ->
                                                     new SocialStrataNotFoundException(
                                                             "No existe el estamento con id: " + id
                                                     )
                                );
        return SocialStrataResponse.fromDomain(socialStrata);
    }
}
