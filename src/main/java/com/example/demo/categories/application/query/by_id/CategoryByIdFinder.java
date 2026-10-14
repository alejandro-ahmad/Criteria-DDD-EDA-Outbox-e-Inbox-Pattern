package com.example.demo.categories.application.query.by_id;

import com.example.demo.categories.application.exceptions.CategoryNotFoundException;
import com.example.demo.categories.application.query.CategoryResponse;
import com.example.demo.categories.application.query.ports.CategoryQueryRepository;
import com.example.demo.shared.domain.Service;


@Service
public class CategoryByIdFinder {
   private final CategoryQueryRepository repository;

    public CategoryByIdFinder(CategoryQueryRepository repository) {
        this.repository = repository;
    }

    public CategoryResponse execute(CategoryByIdQuery query) {
        return repository.findSummaryById(query.id()).orElseThrow(() -> new CategoryNotFoundException(
                String.format("Categoria con ID %s no existe.",query.id())
        ));
    }
}
