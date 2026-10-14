package com.example.demo.categories.infrastructure.input.get.criteria;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.categories.application.query.criteria.CriteriaCategoryQuery;
import com.example.demo.shared.domain.bus.query.QueryBus;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Categories")
public class CategoryCriteriaController {

    private final QueryBus queryBus;

    public CategoryCriteriaController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/private/categories")
    public ResponseEntity<PaginatedResponse<CategoryResponse>> criteria(CategoryCriteriaRequest request) {
        CriteriaCategoryQuery query = new CriteriaCategoryQuery(
                request.toFilters(),
                Optional.ofNullable(request.pagination().sortBy()),
                Optional.ofNullable(request.pagination().sortDirection()),
                Optional.ofNullable(request.pagination().page()),
                Optional.ofNullable(request.pagination().size())
        );

        PaginatedResponse<CategoryResponse> response = queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
