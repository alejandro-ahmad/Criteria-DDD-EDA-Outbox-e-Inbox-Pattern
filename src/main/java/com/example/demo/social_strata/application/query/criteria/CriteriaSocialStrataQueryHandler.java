package com.example.demo.social_strata.application.query.criteria;


import com.example.demo.shared.application.criteria.GenericCriteriaValidator;
import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.query.QueryHandler;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.criteria.Filters;
import com.example.demo.shared.domain.criteria.Order;
import com.example.demo.shared.domain.criteria.Pagination;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.social_strata.application.ports.SocialStrataQueryRepository;
import com.example.demo.social_strata.application.query.SocialStrataResponse;
import com.example.demo.social_strata.domain.valobj.SocialStrataName;

import java.util.Map;
import java.util.function.Consumer;

@Service
public class CriteriaSocialStrataQueryHandler implements QueryHandler<CriteriaSocialStrataQuery, PaginatedResponse<SocialStrataResponse>> {

    private final SocialStrataQueryRepository repository;

    private static final Map<String, Consumer<String>> ALLOWED_FILTERS = Map.of(
            "name", SocialStrataName::create
    );

    public CriteriaSocialStrataQueryHandler(SocialStrataQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaginatedResponse<SocialStrataResponse> handle(CriteriaSocialStrataQuery query) {
        Criteria criteria = new Criteria(
                Filters.fromValues(query.filters()),
                Order.fromValues(query.sortBy(), query.sortDirection()),
                Pagination.fromValues(query.page(),query.size())
        );

        GenericCriteriaValidator.validate(criteria, ALLOWED_FILTERS);

        return repository.search(criteria);
    }
}
