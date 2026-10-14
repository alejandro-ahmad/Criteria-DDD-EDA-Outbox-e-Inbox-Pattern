package com.example.demo.categories.application.query.criteria;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.categories.application.query.ports.CategoryQueryRepository;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.shared.application.criteria.GenericCriteriaValidator;
import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.query.QueryHandler;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.criteria.Filters;
import com.example.demo.shared.domain.criteria.Order;
import com.example.demo.shared.domain.criteria.Pagination;
import com.example.demo.shared.domain.pagination.PaginatedResponse;

import java.util.Map;
import java.util.function.Consumer;

@Service
public class CriteriaCategoryQueryHandler implements QueryHandler<CriteriaCategoryQuery, PaginatedResponse<CategoryResponse>> {

    private final CategoryQueryRepository repository;

    private static final Map<String, Consumer<String>> ALLOWED_FILTERS = Map.of(
            "id", Integer::valueOf,
            "name", CategoryName::create,
            "carnetName", val -> {},
            "socialStrataId", Integer::valueOf,
            "code", Integer::valueOf
    );

    public CriteriaCategoryQueryHandler(CategoryQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaginatedResponse<CategoryResponse> handle(CriteriaCategoryQuery query) {
        Criteria criteria = new Criteria(
                Filters.fromValues(query.filters()),
                Order.fromValues(query.sortBy(), query.sortDirection()),
                Pagination.fromValues(query.page(),query.size())
        );

        GenericCriteriaValidator.validate(criteria, ALLOWED_FILTERS);

        return repository.search(criteria);
    }
}
