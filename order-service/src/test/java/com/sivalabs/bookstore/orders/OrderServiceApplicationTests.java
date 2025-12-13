package com.sivalabs.bookstore.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfig.class)
@SpringBootTest
class OrderServiceApplicationTests extends AbstractIT {

    @Test
    void contextLoads() {}
}
