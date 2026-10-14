package com.example.demo.shared.domain.criteria;

import java.util.Optional;

public record Pagination(int page, int size) {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE = 100;

    public Pagination {
        if(page < 0){
            page = DEFAULT_PAGE;
        }
        if(size <= 0){
            size = DEFAULT_SIZE;
        }
        if(size > MAX_SIZE){
            size = MAX_SIZE;
        }
    }

    public static Pagination of(Integer page, Integer size){
        return new Pagination(page != null ? page : DEFAULT_PAGE,size != null ? size : DEFAULT_SIZE);
    }

    public static Pagination fromValues(Optional<Integer> page, Optional<Integer> size) {
        return of(
                page != null ? page.orElse(null) : null,
                size != null ? size.orElse(null) : null
        );
    }
}
