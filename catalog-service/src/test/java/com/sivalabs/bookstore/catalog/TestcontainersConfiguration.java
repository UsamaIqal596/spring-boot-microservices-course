package com.sivalabs.bookstore.catalog;

import org.springframework.boot.SpringApplication;

class TestcontainersConfiguration {

    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
