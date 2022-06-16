package com.leovegas.walletservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * Swagger configuration.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApiV1() {
        return GroupedOpenApi.builder()
                .group("public-api-v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(Environment env) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title(env.getProperty("spring.application.name", "wallet-service"))
                        .version("0.0.1")
                        .description("Wallet-service public API")
                        .contact(new Contact()
                                .name("Alexey SUGROBOV")
                                .email("aleksey.sugrobov@gmail.com"))
                );
    }

}
