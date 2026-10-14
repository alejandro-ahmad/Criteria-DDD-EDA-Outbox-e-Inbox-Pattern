package com.example.demo.categories.infrastructure.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity,String>, JpaSpecificationExecutor<CategoryEntity> {
    boolean existsByNameAndIdNot(String name,String id);
    boolean existsByName(String name);
    boolean existsByCode(Integer code);
}
