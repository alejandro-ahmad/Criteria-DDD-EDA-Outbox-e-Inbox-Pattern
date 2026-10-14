package com.example.demo.categories.infrastructure.output.persistence;

import com.example.demo.categories.domain.CategoryRepository;
import com.example.demo.categories.domain.model.Category;
import com.example.demo.categories.domain.valobj.CategoryId;
import com.example.demo.categories.domain.valobj.CategoryName;
import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.shared.infrastructure.persistence.criteria.JpaCriteriaConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryPersistenceAdapter implements CategoryRepository {

    private final JpaCategoryRepository repository;
    private final CategoryPersistenceMapper mapper;

    public CategoryPersistenceAdapter(JpaCategoryRepository repository, CategoryPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return repository.findById(id.value().toString()).map(mapper::toDomain);
    }

    @Override
    public void save(Category category) {
        repository.save(mapper.toEntity(category));
    }

    @Override
    public boolean existsByName(CategoryName name) {
        return repository.existsByName(name.value());
    }

    @Override
    public boolean existsByCode(Integer code) {
        return repository.existsByCode(code);
    }

    @Override
    public boolean existsByNameAndIdNot(CategoryName name, CategoryId id) {
        return repository.existsByNameAndIdNot(name.value(), id.value().toString());
    }

    @Override
    public PaginatedResponse<Category> search(Criteria criteria) {
        Specification<CategoryEntity> specification = JpaCriteriaConverter.convert(criteria);

        Sort sort = Sort.unsorted();
        if (criteria.order().hasOrder()) {
            Sort.Direction direction = criteria.order().orderType().isAsc()
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
            sort = Sort.by(direction, criteria.order().orderBy().value());
        }

        Pageable pageable = PageRequest.of(
                criteria.pagination().page(),
                criteria.pagination().size(),
                sort
        );

        Page<CategoryEntity> page = repository.findAll(specification, pageable);

        List<Category> categories = page.getContent().stream()
                                        .map(mapper::toDomain)
                                        .toList();

        return new PaginatedResponse<>(
                categories,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
