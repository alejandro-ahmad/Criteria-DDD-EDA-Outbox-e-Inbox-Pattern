package com.example.demo.social_strata.infrastructure.input.get.criteria;

import com.example.demo.shared.domain.bus.query.QueryBus;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.shared.infrastructure.persistence.criteria.PageParams;
import com.example.demo.social_strata.application.query.SocialStrataResponse;
import com.example.demo.social_strata.application.query.criteria.CriteriaSocialStrataQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Social Strata (Estamentos)")
public class SocialStrataCriteriaController {
    private final QueryBus queryBus;

    public SocialStrataCriteriaController(QueryBus queryBus){
        this.queryBus = queryBus;
    }


    @GetMapping("/private/social-strata")
    public ResponseEntity<PaginatedResponse<SocialStrataResponse>> criteria(SocialStrataCriteriaRequest request){

        PageParams pagination = request.pagination() != null
                ? request.pagination()
                : new PageParams(null, null, null, null);

        CriteriaSocialStrataQuery query = new CriteriaSocialStrataQuery(
                request.toFilters(),
                Optional.ofNullable(pagination.page()),
                Optional.ofNullable(pagination.size()),
                Optional.ofNullable(pagination.sortBy()),
                Optional.ofNullable(pagination.sortDirection())
        );

        PaginatedResponse<SocialStrataResponse> response = queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
