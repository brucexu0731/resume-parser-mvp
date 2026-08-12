package com.brucexu.springBootBackend.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the application.
 *
 * <p>This configuration allows the local React development server
 * (http://localhost:5173) to make HTTP requests to the upload-related
 * endpoints exposed by the Spring Boot backend. It is primarily intended
 * for local development where the frontend and backend run on different
 * origins.</p>
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/uploads/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}