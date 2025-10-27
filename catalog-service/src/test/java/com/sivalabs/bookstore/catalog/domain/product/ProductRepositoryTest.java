package com.sivalabs.bookstore.catalog.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(
        properties = {
                "spring.test.database.replace=none", //bydefault @DataJpaTest tries to connect with the H2 Database so we disabled that by this line
                "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db",
        })
// we can use this annotation to import the ContainersConfig class but let say our configuration calss is also
//containning the other configuration like to sping up rabitMQ so thsi is not a good approach to do this
//as we are just testing the Repository Layer use the above properties to configure the datasource container
// @Import(ContainersConfig.class)
@Sql("/test-data.sql")
class ProductRepositoryTest { //this class is an Exmplae of a Slice Test where we are tesing only a single layer like repository


    //I used @DataJpaTest, which is a slice test annotation.
    //It loads only the persistence layer beans like repositories, entities, DataSource, and JPA configuration,
    //  not the entire Spring Boot application context.
    //    it does not load Services, Controllers, Security configs,Web, MVC, RabbitMQ, Kafka beans


    //we can check by using below
    @Autowired
    ApplicationContext ctx;

    @Test
    void debugBeans() {
        Arrays.stream(ctx.getBeanDefinitionNames())
                .forEach(System.out::println);
    }





    @Autowired
    private ProductRepository productRepository;

    // You don't need to test the methods provided by Spring Data JPA.
    // This test is to demonstrate how to write tests for the repository layer.
    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void shouldGetProductByCode() {
        ProductEntity prod = productRepository.findByCode("P100").orElseThrow();
        assertThat(prod.getCode()).isEqualTo("P100");
        assertThat(prod.getName()).isEqualTo("The Hunger Games");
        assertThat(prod.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(prod.getPrice()).isEqualTo(new BigDecimal("34.0"));

        // recommended modren assertj format

        assertThat(productRepository.findByCode("P100"))
                .isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getCode()).isEqualTo("P100");
                    assertThat(product.getName()).isEqualTo("The Hunger Games");
                    assertThat(product.getDescription())
                            .isEqualTo("Winning will make you famous. Losing means certain death...");
                    assertThat(product.getPrice()).isEqualByComparingTo("34.0");
                });

        //for Clarity older Junit format
        Optional<ProductEntity> result = productRepository.findByCode("P100");
        assertTrue(result.isPresent());

        ProductEntity product = result.get();
        assertEquals("P100", product.getCode());
        assertEquals("The Hunger Games", product.getName());
        assertEquals("Winning will make you famous. Losing means certain death...", product.getDescription());
        assertEquals(new BigDecimal("34.0"), product.getPrice());


    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists() {
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
        //other possible same ways
        assertThat(productRepository.findByCode("invalid_product_code")).isNotPresent();
        Optional<ProductEntity> result = productRepository.findByCode("invalid_product_code");
        assertTrue(result.isEmpty());


        // thsi will not work as jpa repo returns Optional empth in case of in valid code, so we can use above methods
//        assertThatThrownBy(() -> productRepository.findByCode("invalid_product_code"))
//                .isInstanceOf(ProductNotFoundException.class)
//                .hasMessageContaining("invalid_product_code");


    }
}
