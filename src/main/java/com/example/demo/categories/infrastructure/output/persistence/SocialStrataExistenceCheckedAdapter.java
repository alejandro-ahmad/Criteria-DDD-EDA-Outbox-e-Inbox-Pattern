package com.example.demo.categories.infrastructure.output.persistence;

import com.example.demo.categories.domain.SocialStrataExistenceChecker;
import com.example.demo.categories.domain.valobj.SocialStrataId;
import org.springframework.stereotype.Component;

@Component
public class SocialStrataExistenceCheckedAdapter implements SocialStrataExistenceChecker {
    private final JpaCachedSocialStrataRepository repository;

    public SocialStrataExistenceCheckedAdapter(JpaCachedSocialStrataRepository repository){
        this.repository = repository;
    }

    @Override
    public boolean exists(SocialStrataId id){
        return repository.existsById(id.value());
    }
}
