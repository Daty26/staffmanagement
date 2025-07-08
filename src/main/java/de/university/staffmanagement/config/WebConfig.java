package de.university.staffmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class for enabling Cross-Origin Resource Sharing (CORS).
 *
 * <p>This configuration allows the frontend (e.g. React app) running on localhost:8087
 * to make API calls to the backend. CORS settings are applied to all `/api/**` endpoints.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Defines CORS rules for API endpoints.
     *
     * @param registry the CORS registry to configure
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //change port for your frontend port app
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8087")
                .allowedMethods("GET", "POST", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}