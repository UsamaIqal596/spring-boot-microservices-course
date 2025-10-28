package com.sivalabs.bookstore.catalog.domain.product;

import java.math.BigDecimal;

public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
