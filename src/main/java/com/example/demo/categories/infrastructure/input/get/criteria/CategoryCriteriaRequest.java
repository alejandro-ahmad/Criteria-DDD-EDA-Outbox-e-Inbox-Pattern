package com.example.demo.categories.infrastructure.input.get.criteria;

import com.example.demo.shared.infrastructure.persistence.criteria.PageParams;
import org.springdoc.core.annotations.ParameterObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParameterObject
public record CategoryCriteriaRequest(
        PageParams pagination,
        Integer id,
        String name,
        String carnetName,
        String socialStrataId,
        Integer code
) {
    public CategoryCriteriaRequest {
        if (pagination == null) {
            pagination = new PageParams(null, null, null, null);
        }
    }

    public List<Map<String, String>> toFilters() {
        List<Map<String, String>> filters = new ArrayList<>();

        if (id != null) {
            filters.add(buildFilter("id", "=", String.valueOf(id)));
        }
        if (name != null && !name.isBlank()) {
            filters.add(buildFilter("name", "CONTAINS", name));
        }
        if (carnetName != null && !carnetName.isBlank()) {
            filters.add(buildFilter("carnetName", "CONTAINS", carnetName));
        }
        if (socialStrataId != null) {
            filters.add(buildFilter("socialStrataId", "=", socialStrataId));
        }
        if (code != null) {
            filters.add(buildFilter("code", "=", String.valueOf(code)));
        }

        return filters;
    }

    private Map<String, String> buildFilter(String field, String operator, String value) {
        Map<String, String> map = new HashMap<>();
        map.put("field", field);
        map.put("operator", operator);
        map.put("value", value);
        return map;
    }
}
