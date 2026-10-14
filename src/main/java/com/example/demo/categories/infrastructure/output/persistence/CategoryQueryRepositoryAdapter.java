package com.example.demo.categories.infrastructure.output.persistence;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.categories.application.query.ports.CategoryQueryRepository;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.criteria.Filter;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.social_strata.infrastructure.persistence.SocialStrataEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryQueryRepositoryAdapter implements CategoryQueryRepository {

    private final JpaCategoryQueryRepository queryRepository;
    private final EntityManager entityManager;

    public CategoryQueryRepositoryAdapter(JpaCategoryQueryRepository queryRepository, EntityManager entityManager) {
        this.queryRepository = queryRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<CategoryResponse> findSummaryById(String id) {
        return queryRepository.findSummaryById(id);
    }

    @Override
    public PaginatedResponse<CategoryResponse> search(Criteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        int page = criteria.pagination().page();
        int size = criteria.pagination().size();

        // 1. Consulta Principal de Datos
        CriteriaQuery<CategoryResponse> cq = cb.createQuery(CategoryResponse.class);
        Root<CategoryEntity>                     category = cq.from(CategoryEntity.class);
        Join<CategoryEntity, SocialStrataEntity> strata   = category.join("socialStrata", JoinType.LEFT);

        List<Predicate> predicates = buildPredicates(criteria, category, cb);
        cq.where(predicates.toArray(new Predicate[0]));

        cq.select(cb.construct(
                CategoryResponse.class,
                category.get("id"),
                category.get("name"),
                category.get("carnetName"),
                category.get("socialStrataId"),
                strata.get("name"),
                category.get("code")
        ));

        // Ordenamiento
        if (criteria.order().hasOrder()) {
            Path<?> sortPath = category.get(criteria.order().orderBy().value());
            cq.orderBy(criteria.order().orderType().isAsc() ? cb.asc(sortPath) : cb.desc(sortPath));
        }

        TypedQuery<CategoryResponse> query = entityManager.createQuery(cq);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<CategoryResponse> content = query.getResultList();

        // 2. Consulta de Conteo Total
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<CategoryEntity> countRoot = countCq.from(CategoryEntity.class);
        countCq.select(cb.count(countRoot));
        countCq.where(buildPredicates(criteria, countRoot, cb).toArray(new Predicate[0]));
        Long totalElements = entityManager.createQuery(countCq).getSingleResult();

        int totalPages = (size == 0) ? 1 : (int) Math.ceil((double) totalElements / (double) size);

        return new PaginatedResponse<>(content, page, size, totalElements, totalPages);
    }

    private List<Predicate> buildPredicates(Criteria criteria, Root<CategoryEntity> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!criteria.hasFilters()) {
            return predicates;
        }

        for (Filter filter : criteria.filters().filters()) {
            String field = filter.field().field();
            String value = filter.value().value();
            Path<?> path = root.get(field);

            switch (filter.operator()) {
                case EQUALS -> predicates.add(cb.equal(path, value));
                case CONTAINS -> predicates.add(
                        cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%")
                );
                default -> {}
            }
        }
        return predicates;
    }
}
