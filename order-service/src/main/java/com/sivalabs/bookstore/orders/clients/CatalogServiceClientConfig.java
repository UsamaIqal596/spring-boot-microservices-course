package com.sivalabs.bookstore.orders.clients;

import com.sivalabs.bookstore.orders.ApplicationProperties;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationProperties properties) {
        // Create the request factory directly
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        requestFactory.setReadTimeout((int) Duration.ofSeconds(5).toMillis());

        return RestClient.builder()
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(requestFactory) // Pass the configured factory here
                .build();
    }
}
