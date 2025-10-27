package com.sivalabs.bookstore.catalog.domain.product;

import java.util.List;

public record PagedResult<T>(
        List<T> data,
        long totalElements,
        int totalPages,
        int pageNumber,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {





}