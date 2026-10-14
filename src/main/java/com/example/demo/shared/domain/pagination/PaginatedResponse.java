package com.example.demo.shared.domain.pagination;

import com.example.demo.shared.domain.bus.query.Response;

import java.util.List;
import java.util.function.Function;

public record PaginatedResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) implements Response {

    public <R> PaginatedResponse<R> map(Function<T, R> mapper) {
        List<R> mappedContent = this.content != null
                ? this.content.stream().map(mapper).toList()
                : List.of();
        return new PaginatedResponse<>(mappedContent, this.page, this.size, this.totalElements, this.totalPages);
    }
}
