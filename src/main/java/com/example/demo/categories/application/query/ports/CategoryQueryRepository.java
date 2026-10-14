package com.example.demo.categories.application.query.ports;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.pagination.PaginatedResponse;

import java.util.Optional;

public interface CategoryQueryRepository {
    Optional<CategoryResponse> findSummaryById(String id);
    PaginatedResponse<CategoryResponse> search(Criteria criteria);
}
