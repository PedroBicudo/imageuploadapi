package io.github.pedrobicudo.imageuploadapi.model.domain.services.implementations;

import io.github.pedrobicudo.imageuploadapi.model.domain.entities.Image;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.*;
import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces.IImageService;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImagePath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private static final int MAX_IMAGE_SIZE = 5242880; // 5MB

    @Autowired
    private ImageRepository repository;

    @Override
    @Transactional
    public ImagePath upload(MultipartFile file) {
        validateImage(file);
        ImagePath imagePath = new ImagePath();
        try {
            Image image = new Image();
            image.setContent(toWrapperByte(file.getBytes()));
            image.setMediaType(file.getContentType());
            repository.save(image);
            imagePath.setImageId(image.getId());

        } catch (IOException exception) {
            throw new InvalidImageException();

        }

        return imagePath;
    }

    private Byte[] toWrapperByte(byte[] content) {
        Byte[] contentObj = new Byte[content.length];
        for (int pos = 0; pos < contentObj.length; pos++) {
            contentObj[pos] = content[pos];
        }

        return contentObj;
    }

    private void validateImage(MultipartFile file) {
        if (file == null) throw new ImageNotProvidedException();
        if (file.isEmpty()) throw new ImageEmptyException();
        if (!isLessThanMaximumSize(file)) throw new ImageTooBigException();
        if (!isImage(file)) throw new NotAImageException();

    }

    private boolean isLessThanMaximumSize(MultipartFile file) {
        try {
            return file.getBytes().length <= MAX_IMAGE_SIZE;

        } catch (IOException exception) {
            return false;

        }
    }

    private boolean isImage(MultipartFile file) {
        try {
            return file.getContentType().startsWith("image/");

        } catch (RuntimeException e) {
            return false;
        }
    }
}
