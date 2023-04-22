package com.sda.clinicapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic"
)
public class SwaggerConfig {

    @Value("${application.version}")
    private String version;

    @Bean
    public GroupedOpenApi clinicsApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("com.sda.clinicapi.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {

        License license = new License().name("Apache 2.0");

        Contact contact = new Contact()
                .name("ACME ORG")
                .email("example@gmail.com")
                .url("https://example.com");

        Info info = new Info().title("Clinics API")
                .contact(contact)
                .description("API for doctors appointment management")
                .version(version)
                .license(license);

        return new OpenAPI().info(info);
    }
}
