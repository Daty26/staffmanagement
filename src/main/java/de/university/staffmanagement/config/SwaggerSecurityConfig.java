package de.university.staffmanagement.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


/**
 * Configures Swagger/OpenAPI to use JWT-based authentication.
 *
 * <p>This setup enables Swagger UI to authorize requests using the JWT token format
 * by adding a "Authorize" button with a Bearer token input field.
 */
@Configuration
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerSecurityConfig {

}
