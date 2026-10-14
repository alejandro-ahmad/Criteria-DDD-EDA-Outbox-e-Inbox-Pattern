package com.example.demo.categories.application.query.by_id;

import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class CategoryByIdQueryHandler implements QueryHandler<CategoryByIdQuery, CategoryResponse> {
    private final CategoryByIdFinder finder;

    public CategoryByIdQueryHandler(CategoryByIdFinder finder){
        this.finder = finder;
    }

    @Override
    public CategoryResponse handle(CategoryByIdQuery query){
        return finder.execute(query);
    }
}
