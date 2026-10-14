package com.example.demo.shared.domain.criteria;

import java.util.HashMap;
import java.util.Map;

public record Filter(FilterField field, FilterOperator operator,FilterValue value) {
    public static Filter create(String field,String value,String operator){
        return new Filter(
                new FilterField(field), FilterOperator.fromString(operator), new FilterValue(value)
        );
    }

    public static Filter fromValues(Map<String,String> values){
        return new Filter(
                new FilterField(values.get("field")),
                FilterOperator.fromString(values.get("operator")),
                new FilterValue(values.get("value"))
        );
    }

    public Map<String, String> serialize() {
        Map<String, String> map = new HashMap<>();
        map.put("field", field.field());
        map.put("operator", operator.value());
        map.put("value",value.value());
        return map;
    }
}
