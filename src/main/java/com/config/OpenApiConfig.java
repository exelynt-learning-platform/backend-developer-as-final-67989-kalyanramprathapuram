package com.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // =============================================
                // API INFORMATION
                // =============================================

                .info(
                    new Info()
                        .title("Resource Booking API")
                        .version("1.0.0")
                        .description(
                            "REST API for resource booking, " +
                            "reservations and user authentication."
                        )
                        .contact(
                            new Contact()
                                .name("Resource Booking API")
                        )
                )

                // =============================================
                // JWT SECURITY
                // =============================================

                .components(
                    new Components()
                        .addSecuritySchemes(
                            "Bearer Authentication",
                            new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}