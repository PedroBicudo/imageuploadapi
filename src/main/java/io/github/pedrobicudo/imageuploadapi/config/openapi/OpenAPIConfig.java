package io.github.pedrobicudo.imageuploadapi.config.openapi;

import io.github.pedrobicudo.imageuploadapi.model.domain.properties.OpenAPIProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Autowired
    private OpenAPIProperties openAPIProperties;

    @Bean
    public OpenAPI openAPICustomConfig() {
        return new OpenAPI()
                .components(new Components())
                .info(openAPIInfo());
    }

    private Info openAPIInfo() {
        return new Info()
                .title(openAPIProperties.getApplication().getTitle())
                .description(openAPIProperties.getApplication().getDescription())
                .contact(openAPIContact());
    }

    private Contact openAPIContact() {
        return new Contact()
                .name(openAPIProperties.getContact().getName())
                .email(openAPIProperties.getContact().getEmail())
                .url(openAPIProperties.getContact().getUrl());
    }

}
