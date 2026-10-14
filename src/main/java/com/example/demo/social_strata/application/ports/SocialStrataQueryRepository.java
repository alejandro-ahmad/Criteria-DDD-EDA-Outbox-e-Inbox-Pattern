package com.example.demo.social_strata.application.ports;

import com.example.demo.shared.domain.criteria.Criteria;
import com.example.demo.shared.domain.pagination.PaginatedResponse;
import com.example.demo.social_strata.application.query.SocialStrataResponse;


public interface SocialStrataQueryRepository {
    PaginatedResponse<SocialStrataResponse> search(Criteria criteria);
}
