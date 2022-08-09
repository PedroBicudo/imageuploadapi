package io.github.pedrobicudo.imageuploadapi.model.domain.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("openapi-info")
@Getter
@Setter
public class OpenAPIProperties {

    private Application application;
    private Contact contact;

    @Setter
    @Getter
    public static class Application {
        private String title;
        private String description;
    }

    @Setter
    @Getter
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }
}
