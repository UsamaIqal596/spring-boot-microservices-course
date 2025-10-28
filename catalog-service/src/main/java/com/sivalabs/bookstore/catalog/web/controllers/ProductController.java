package com.sivalabs.bookstore.catalog.web.controllers;

import com.sivalabs.bookstore.catalog.domain.product.PagedResult;
import com.sivalabs.bookstore.catalog.domain.product.Product;
import com.sivalabs.bookstore.catalog.domain.product.ProductNotFoundException;
import com.sivalabs.bookstore.catalog.domain.product.ProductService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductById(@PathVariable String code) {
        final Optional<Product> productByCode = productService.getProductByCode(code);
        final ResponseEntity<Product> productResponseEntity = productByCode
                .map(body -> ResponseEntity.ok(body))
                // .map(ResponseEntity::ok)//this can be used but above is for more clarity
                // .orElseThrow(() -> new ProductNotFoundException(("Product not found ") + code)); //one of the
                // approach but not recommended
                .orElseThrow(() -> ProductNotFoundException.forCode(code)); // its recommended
        // .orElseThrow(() -> new RuntimeException("Product not found for code " + code));
        return productResponseEntity;
    }
}
