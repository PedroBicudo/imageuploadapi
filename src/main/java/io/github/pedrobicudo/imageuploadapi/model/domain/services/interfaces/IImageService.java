package io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces;

import io.github.pedrobicudo.imageuploadapi.rest.dto.ImagePath;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    ImagePath upload(MultipartFile file);
}