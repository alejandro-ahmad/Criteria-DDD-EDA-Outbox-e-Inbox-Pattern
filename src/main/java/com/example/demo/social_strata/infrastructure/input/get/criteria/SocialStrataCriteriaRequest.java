package com.example.demo.social_strata.infrastructure.input.get.criteria;

import com.example.demo.shared.infrastructure.persistence.criteria.PageParams;
import org.springdoc.core.annotations.ParameterObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParameterObject
public record SocialStrataCriteriaRequest(
        PageParams pagination,
        String name
) {



    public List<Map<String,String>> toFilters(){
        List<Map<String,String>> filters = new ArrayList<>();

        if(name != null){
            filters.add(buildFilter("name","CONTAINS",name));
        }

        return filters;
    }

    public Map<String,String> buildFilter(String field,String operator,String value){
        Map<String,String> map = new HashMap<>();
        map.put("field",field);
        map.put("operator",operator);
        map.put("value",value);
        return map;
    }
}
