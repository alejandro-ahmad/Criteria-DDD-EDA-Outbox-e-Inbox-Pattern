package com.example.demo.social_strata.domain;


import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;

import java.util.Optional;

public interface SocialStrataRepository {
    void save(SocialStrata shift);
    Optional<SocialStrata> findById(SocialStrataId id);
    void delete(SocialStrataId id);
    boolean existsByName(SocialStrataName name);
    boolean existsByNameAndIdNot(SocialStrataName name, SocialStrataId id);
}
