package com.sivalabs.bookstore.orders.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    // by default spring will call retry three times can be configured from the properties manually
    public Optional<Product> getProductByCode(String code) {
        log.info("Fetching product for code: {}", code);
        //        try {

        // CALLING EXTERNAL SERVICE FROM BY USING REST CLIENT FROM oRDER SERVICE -----TO------> Catalog SERVICE
        var product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);

        return Optional.ofNullable(product);
        //            if i dont catch he exception here it will throw geneuc exception and global(Exception.class
        // will handle this)

        //        i have commented this try catch to show the behavior of failing instead of passing to hit the retry
        // mechanism
        //        } catch (Exception e) {
        //            log.info("Error while fetching product for code: {}", code,e);
        //            return Optional.empty(); // this will tell parent class that you need to handle Product
        //             client.getProductByCode(item.code()).orElseThrow(() -> new InvalidOrderException("Invalid Product
        // code:"
        // + item.code()));
        //        }
    }

    Optional<Product> getProductByCodeFallback(String code, Throwable t) {
        log.info("catalog-service get product by code fallback: code:{}, Error: {} ", code, t.getMessage());
        return Optional.empty();
    }
}
