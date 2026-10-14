package com.example.demo.shared.infrastructure.persistence.criteria;

import com.example.demo.shared.domain.pagination.PageNormalizer;
import org.springdoc.core.annotations.ParameterObject;

import java.util.Objects;

@ParameterObject
public record PageParams(
        Integer page,
        Integer size,
        String sortBy,
        String sortDirection
) {
    public PageParams {
        page = Objects.requireNonNullElse(page, PageNormalizer.DEFAULT_PAGE);
        size = Objects.requireNonNullElse(size, PageNormalizer.DEFAULT_SIZE);
        sortBy = Objects.requireNonNullElse(sortBy, "id");
        sortDirection = Objects.requireNonNullElse(sortDirection, "DESC");
    }
}
