package com.example.demo.social_strata.application.query.criteria;

import com.example.demo.shared.domain.bus.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record CriteriaSocialStrataQuery(
        List<Map<String, String>> filters,
        Optional<Integer> page,
        Optional<Integer> size,
        Optional<String> sortBy,
        Optional<String> sortDirection
) implements Query {}
