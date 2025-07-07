package de.university.staffmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Configuration class for setting up OpenAPI documentation using Swagger.
 *
 * <p>This class defines general metadata for the API such as the title, version, and description.
 * It is used by Swagger UI to generate interactive API documentation.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Staff Management System",
                version= "1.0",
                description = "API documentation for KTSA website"
        )
)
public class OpenApiConfig {

}
