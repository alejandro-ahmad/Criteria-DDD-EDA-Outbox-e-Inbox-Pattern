package com.example.demo.shared.domain.pagination;

public final class PageNormalizer {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE = 100;

    private PageNormalizer() {}

    public static int sanitizePage(Integer page) {
        if (page == null || page < 0) {
            return DEFAULT_PAGE;
        }
        return page;
    }

    public static int sanitizeInitialSize(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_SIZE;
        }
        return size;
    }

    public static int clampSizeToTotal(int requestedSize, long totalElements) {
        if (totalElements <= 0) {
            return Math.max(1, requestedSize);
        }

        if (requestedSize == -1 || requestedSize > totalElements) {
            return (int) totalElements;
        }

        return requestedSize;
    }
}
