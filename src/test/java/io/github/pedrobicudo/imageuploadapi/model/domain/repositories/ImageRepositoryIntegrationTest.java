package io.github.pedrobicudo.imageuploadapi.model.domain.repositories;

import io.github.pedrobicudo.imageuploadapi.model.domain.entities.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.PersistenceException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ImageRepositoryIntegrationTest {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Container
    private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:14.2-alpine");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> {
            return container.getJdbcUrl()+"?currentSchema=public";
        });
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeEach
    public void clearTable() {
        entityManager.getEntityManager()
                .createQuery("DELETE FROM Image")
                .executeUpdate();
    }

    @Test
    @DisplayName("create image")
    public void testCreateImage() {
        Image image = new Image();
        image.setContent(new Byte[] {0b0, 0b0, 0b0, 0b0});
        entityManager.persist(image);

        assertNotNull(image.getId());

        Optional<Image> imageFound = repository.findById(image.getId());
        assertThat(imageFound).isPresent();
    }

    @Test
    @DisplayName("content must be not null")
    public void testContentMustNotBeNull() {
        Image image = new Image(null, null, null, null);

        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(image);
        });

    }

    @Test
    @DisplayName("mediaType must be not null")
    public void testMediaTypeMustNotBeNull() {
        Image image = new Image(null, new Byte[] {0x1}, null, null);

        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(image);
        });

    }

}