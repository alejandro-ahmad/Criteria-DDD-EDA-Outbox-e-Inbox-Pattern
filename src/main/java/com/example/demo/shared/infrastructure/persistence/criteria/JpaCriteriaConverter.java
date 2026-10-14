package com.example.demo.shared.infrastructure.persistence.criteria;

import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.criteria.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class JpaCriteriaConverter {

    private JpaCriteriaConverter() {}

    public static <T> Specification<T> convert(Criteria criteria) {
        return (root, query, cb) -> {
            if (!criteria.hasFilters()) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            for (Filter filter : criteria.filters().filters()) {
                predicates.add(buildPredicate(filter, root, cb));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> Predicate buildPredicate(Filter filter, Root<T> root, CriteriaBuilder cb) {
        String field = filter.field().field();
        String value = filter.value().value();
        Path<?> path = root.get(field);
        Class<?> type = path.getJavaType();

        return switch (filter.operator()) {
            case EQUALS -> cb.equal(path, castValue(type, value));
            case NOT_EQUALS -> cb.notEqual(path, castValue(type, value));
            case CONTAINS -> cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%");
            case NOT_CONTAINS -> cb.notLike(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%");
            case GT -> cb.greaterThan(root.get(field), value);
            case LT -> cb.lessThan(root.get(field), value);
        };
    }

    private static Object castValue(Class<?> type, String value) {
        if (Integer.class.isAssignableFrom(type)) return Integer.valueOf(value);
        if (Long.class.isAssignableFrom(type)) return Long.valueOf(value);
        if (Boolean.class.isAssignableFrom(type)) return Boolean.valueOf(value);
        if (type.isEnum()) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Enum<?> enumVal = Enum.valueOf((Class<Enum>) type, value.toUpperCase());
            return enumVal;
        }
        return value;
    }
}
