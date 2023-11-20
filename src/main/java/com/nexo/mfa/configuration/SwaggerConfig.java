package com.nexo.mfa.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPIInformation() {
       return new OpenAPI()
                .info(new Info().title("MFA API")
                        .description("Building a Microservice for Multi-Factor Authentication (MFA) with Java, Spring, and Docker")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                );
    }
}
