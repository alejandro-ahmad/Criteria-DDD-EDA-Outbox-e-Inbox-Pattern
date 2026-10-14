package com.example.demo.categories.infrastructure.output.persistence;

import com.example.demo.categories.application.query.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCategoryQueryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("""
        SELECT new com.example.demo.categories.application.query.CategoryResponse(
            c.id,
            c.name,
            c.carnetName,
            c.socialStrataId,
            s.name,
            c.code
        )
        FROM CategoryEntity c
        LEFT JOIN CachedSocialStrataEntity s ON c.socialStrataId = s.id
        WHERE c.id = :id
    """)
    Optional<CategoryResponse> findSummaryById(@Param("id") String id);
}
