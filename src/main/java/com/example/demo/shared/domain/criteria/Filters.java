package com.example.demo.shared.domain.criteria;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record Filters(List<Filter> filters) {
    public Filters{
        filters = filters != null ? List.copyOf(filters) : Collections.emptyList();
    }

    public static Filters none(){
        return new Filters(Collections.emptyList());
    }

    public static Filters fromValues(List<Map<String,String>> filters){
        if( filters == null || filters.isEmpty()){
            return none();
        }
        return new Filters(filters.stream().map(Filter::fromValues).toList());
    }
}
