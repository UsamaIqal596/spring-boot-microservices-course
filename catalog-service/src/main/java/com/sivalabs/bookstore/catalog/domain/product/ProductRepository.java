package com.sivalabs.bookstore.catalog.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {

   Optional<ProductEntity> findByCode(String code);


}
