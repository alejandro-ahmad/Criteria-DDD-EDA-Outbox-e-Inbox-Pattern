package com.example.demo.categories.domain;

import com.example.demo.categories.domain.model.Category;
import com.example.demo.categories.domain.valobj.CategoryId;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.pagination.PaginatedResponse;

import java.util.Optional;

public interface CategoryRepository {
    void save(Category category);
    Optional<Category> findById(CategoryId id);
    PaginatedResponse<Category> search(Criteria filters);
    boolean existsByName(CategoryName name);
    boolean existsByCode(Integer code);
    boolean existsByNameAndIdNot(CategoryName name, CategoryId id);
}
