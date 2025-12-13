package com.sivalabs.bookstore.orders.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

// TODO:  i need to make this code un commented if i want to allow calls from browser/swagger ui than that e.g from
// postman it will work without this or even ifi call the gateway api directly from the browser
// e.g http://localhost:8989/api/catalog/api/products as this gate internally calls the catalog so its can worked fine
// without the cros in this case as can comment this below whole code
public class WebMvcConfig implements WebMvcConfigurer {

    // TODO: The method addCorsMappings(CorsRegistry registry) is a default method defined in WebMvcConfigurer with an
    // empty
    //    implementation. This means:
    // Introduced in Java 8, default methods allow interfaces to include methods with default implementations without
    // forcing implementing classes to define those methods.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // explain below each why we set these values
        // The addMapping("/**") method specifies that the CORS configuration applies to all endpoints in the
        // application.
        // The allowedOrigins("*") method allows requests from any origin. In a production environment,
        // you might want to restrict this to specific domains for security reasons.
        // The allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") method specifies which HTTP methods are
        // permitted for cross-origin requests.
        // The allowedHeaders("*") method allows all headers in the CORS requests.
        // The allowCredentials(false) method indicates that user credentials (such as cookies, HTTP authentication, and
        // client-side SSL certificates)
        // should not be included in cross-origin requests.
        // Overall, this configuration enables broad CORS support for the application, allowing it to handle requests
        // from any origin with various HTTP methods and headers.
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowedOriginPatterns("*") // Allows dynamic/wildcard origins (handles varying ports and subdomains).
                // Restrict to specific patterns in production for security.
                .allowCredentials(false);
    }
}
