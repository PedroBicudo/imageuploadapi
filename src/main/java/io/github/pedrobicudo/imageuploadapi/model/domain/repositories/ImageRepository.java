package io.github.pedrobicudo.imageuploadapi.model.domain.repositories;

import io.github.pedrobicudo.imageuploadapi.model.domain.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    void deleteByCreatedLessThanEqual(Date date);

}
