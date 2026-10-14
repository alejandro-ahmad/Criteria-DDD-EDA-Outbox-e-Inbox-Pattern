package com.example.demo.social_strata.infrastructure.input.get.by_id;

import com.example.demo.shared.domain.bus.query.QueryBus;
import com.example.demo.social_strata.application.query.SocialStrataResponse;
import com.example.demo.social_strata.application.query.find_by_id.SocialStrataFindQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Social Strata (Estamentos)")
public class SocialStrataGetByIdController {
    private final QueryBus queryBus;

    public SocialStrataGetByIdController(QueryBus queryBus){
        this.queryBus = queryBus;
    }

    @GetMapping("/private/social-strata/{id}")
    public ResponseEntity<SocialStrataResponse> find(@PathVariable String id){
        SocialStrataResponse response = queryBus.ask(new SocialStrataFindQuery(id));

        return ResponseEntity.ok(response);
    }
}
