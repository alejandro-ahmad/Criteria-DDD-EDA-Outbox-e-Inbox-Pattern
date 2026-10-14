package com.example.demo.social_strata.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JpaSocialStrataRepository extends JpaRepository<SocialStrataEntity,String>, JpaSpecificationExecutor<SocialStrataEntity> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
