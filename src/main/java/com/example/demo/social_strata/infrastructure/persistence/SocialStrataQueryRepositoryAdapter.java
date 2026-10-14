package com.example.demo.social_strata.infrastructure.persistence;

import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.shared.infrastructure.persistence.criteria.JpaCriteriaConverter;
import com.example.demo.social_strata.application.ports.SocialStrataQueryRepository;
import com.example.demo.social_strata.application.query.SocialStrataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocialStrataQueryRepositoryAdapter implements SocialStrataQueryRepository {

    private final JpaSocialStrataRepository repository;

    public SocialStrataQueryRepositoryAdapter(JpaSocialStrataRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaginatedResponse<SocialStrataResponse> search(Criteria criteria) {
        Specification<SocialStrataEntity> spec = JpaCriteriaConverter.convert(criteria);

        Sort sort = Sort.unsorted();
        if (criteria.order().hasOrder()) {
            Sort.Direction dir = criteria.order().orderType().isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(dir, criteria.order().orderBy().value());
        }

        Pageable pageable = PageRequest.of(
                criteria.pagination().page(),
                criteria.pagination().size(),
                sort
        );

        Page<SocialStrataEntity> page = repository.findAll(spec, pageable);

        List<SocialStrataResponse> content = page.getContent().stream()
                                                 .map(entity -> new SocialStrataResponse(entity.getId(), entity.getName()))
                                                 .toList();

        return new PaginatedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
