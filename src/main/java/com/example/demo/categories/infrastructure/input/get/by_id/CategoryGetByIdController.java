package com.example.demo.categories.infrastructure.input.get.by_id;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.categories.application.query.by_id.CategoryByIdQuery;
import com.example.demo.shared.domain.bus.query.QueryBus;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Categories")
public class CategoryGetByIdController {
    private final QueryBus queryBus;

    public CategoryGetByIdController(QueryBus queryBus){
        this.queryBus = queryBus;
    }

    @GetMapping("/private/categories/{id}")
    public ResponseEntity<CategoryResponse> byId(@PathVariable String id){
        CategoryByIdQuery query = new CategoryByIdQuery(id);

        CategoryResponse response = queryBus.ask(query);

        return ResponseEntity.ok(response);
    }
}
