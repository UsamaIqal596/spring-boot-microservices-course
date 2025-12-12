package com.sivalabs.bookstore.orders.web.controllers;

import static com.sivalabs.bookstore.orders.testdata.TestDataFactory.*;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.bookstore.orders.domain.OrderService;
import com.sivalabs.bookstore.orders.domain.SecurityService;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerUnitTests {
    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        given(securityService.getLoginUserName()).willReturn("siva");
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    //    @WithMockUser
    void shouldReturnBadRequestWhenOrderPayloadIsInvalid(CreateOrderRequest request) throws Exception {
        given(orderService.createOrder(eq("siva"), any(CreateOrderRequest.class)))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        //                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", createOrderRequestWithNoItems())));
    }

    //    @Test
    //    void shouldReturnBadRequestWhenProductNotFound() throws Exception {
    //        // 1. Prepare a VALID request payload
    //        // (This ensures it passes the @Valid check and actually calls the Service)
    //        CreateOrderRequest request = new CreateOrderRequest(
    //                new Customer("Siva", "siva@gmail.com", "999999999"),
    //                new Address("Birkelweg", "Hans-Edenhofer-Straße 23", "Berlin", "Berlin", "94258", "Germany"),
    //                List.of(new OrderItem("P100", "Product 1", new BigDecimal("34.00"), 1))
    //        );
    //
    //        // 2. Mock the Service behavior
    //        // When createOrder is called, throw the specific exception simulating "Product Not Found"
    //        String exceptionMessage = "Invalid Product code:P100";
    //        RestAssured.given(orderService.createOrder(any(), any(CreateOrderRequest.class)))
    //                .willThrow(new InvalidOrderException(exceptionMessage));
    //
    //        // 3. Perform the request and Assert
    //        mockMvc.perform(post("/api/orders")
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content(objectMapper.writeValueAsString(request)))
    //                .andExpect(status().isBadRequest()) // Assert HTTP 400
    //                .andExpect(jsonPath("$.detail").value(exceptionMessage)) // Verify the error message
    //                .andExpect(jsonPath("$.title").value("Invalid Order Creation Request")); // Verify error title
    //    }

}
