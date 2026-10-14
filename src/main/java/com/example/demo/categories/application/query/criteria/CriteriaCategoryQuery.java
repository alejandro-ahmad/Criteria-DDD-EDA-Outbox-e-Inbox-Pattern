package com.example.demo.categories.application.query.criteria;

import com.example.demo.shared.domain.bus.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record CriteriaCategoryQuery(
        List<Map<String, String>> filters,
        Optional<String> sortBy,
        Optional<String> sortDirection,
        Optional<Integer> page,
        Optional<Integer> size
) implements Query {}
