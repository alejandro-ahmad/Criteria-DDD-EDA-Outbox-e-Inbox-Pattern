package com.example.demo.social_strata.infrastructure.persistence;


import com.example.demo.social_strata.domain.SocialStrataRepository;
import com.example.demo.social_strata.domain.model.SocialStrata;
import com.example.demo.social_strata.domain.valobj.SocialStrataId;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialStrataPersistenceAdapter implements SocialStrataRepository {
    private final JpaSocialStrataRepository     repository;
    private final SocialStrataPersistenceMapper mapper;

    public SocialStrataPersistenceAdapter(JpaSocialStrataRepository repository, SocialStrataPersistenceMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<SocialStrata> findById(SocialStrataId id) {
        return repository.findById(id.value().toString()).map(mapper::toDomain);
    }

    @Override
    public void save(SocialStrata domain) {
        repository.save(mapper.toEntity(domain));
    }

    @Override
    public boolean existsByName(SocialStrataName name) {
        return repository.existsByName(name.value());
    }

    @Override
    public boolean existsByNameAndIdNot(
            SocialStrataName name,
            SocialStrataId id
    ) {
        return repository.existsByNameAndIdNot(
                name.value(),
                id.value()
        );
    }

    @Override
    public void delete(SocialStrataId id) {
        repository.deleteById(id.value().toString());
    }
}
