package com.example.demo.social_strata.application.query.find_by_id;

import com.example.demo.shared.domain.Service;
import com.example.demo.shared.domain.bus.query.QueryHandler;
import com.example.demo.social_strata.application.query.SocialStrataResponse;

@Service
public final class SocialStrataFindQueryHandler implements QueryHandler<SocialStrataFindQuery, SocialStrataResponse> {
    private final SocialStrataFinderById finder;

    public SocialStrataFindQueryHandler(SocialStrataFinderById finder){
        this.finder = finder;
    }

    @Override
    public SocialStrataResponse handle(SocialStrataFindQuery query){
       return finder.find(query.id());
    }
}
