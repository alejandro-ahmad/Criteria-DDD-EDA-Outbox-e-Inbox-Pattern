package com.example.demo.social_strata.infrastructure.persistence;

import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;
import org.springframework.stereotype.Component;


@Component
public class SocialStrataPersistenceMapper {

    public SocialStrataEntity toEntity(SocialStrata domain) {
        if (domain == null) {
            return null;
        }

        SocialStrataEntity entity = new SocialStrataEntity();

        if (domain.id() != null && domain.id().value() != null) {
            entity.setId(domain.id().value().toString());
        }

        if (domain.name() != null) {
            entity.setName(domain.name().value());
        }

        return entity;
    }

    public SocialStrata toDomain(SocialStrataEntity entity) {
        if (entity == null) {
            return null;
        }

        return new SocialStrata(
                entity.getId() != null ? SocialStrataId.fromString(entity.getId()) : null,
                entity.getName() != null ? SocialStrataName.fromPersistence(entity.getName()) : null
        );
    }
}
