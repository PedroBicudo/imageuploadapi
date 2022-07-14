package io.github.pedrobicudo.imageuploadapi.model.domain.services.implementations;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.*;
import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository repository;

    @InjectMocks
    private ImageService service;

    private final MultipartFile mockFile = Mockito.mock(MultipartFile.class);

    @Test
    @DisplayName("image must be not empty")
    public void testImageMustBeNotEmpty() throws IOException {
        Mockito.when(mockFile.isEmpty()).thenReturn(true);

        DomainException exception = assertThrows(ImageEmptyException.class, () -> {
            service.upload(mockFile);
        });

        assertEquals("image is empty", exception.getMessage());
        assertEquals(ErrorCode.IMAGE_EMPTY, exception.getCode());
    }

    @Test
    @DisplayName("image must be not null")
    public void testImageMustBeNotNull() throws IOException {
        DomainException exception = assertThrows(ImageNotProvidedException.class, () -> {
            service.upload(null);
        });

        assertEquals("image not provided", exception.getMessage());
        assertEquals(ErrorCode.IMAGE_NOT_PROVIDED, exception.getCode());
    }

    @Test
    @DisplayName("image with more than 5mb must throw ImageTooBigException")
    public void testImageWithMoreThan5MBMustThrowImageTooBigException() throws IOException {
        Mockito.when(mockFile.getBytes()).thenReturn(new byte[5242881]); // 5MB + 1

        DomainException exception = assertThrows(ImageTooBigException.class, () -> {
            service.upload(mockFile);
        });

        assertEquals("the image size must have at most 5MB", exception.getMessage());
        assertEquals(ErrorCode.IMAGE_TOO_BIG, exception.getCode());
    }

    @Test
    @DisplayName("incorrect image content-type must throw NotAImageException")
    public void testIncorrectImageContentTypeMustThrowNotAImageException() throws IOException {
        Mockito.when(mockFile.getBytes()).thenReturn(new byte[100]);
        Mockito.when(mockFile.getContentType()).thenReturn("application/pdf");

        DomainException exception = assertThrows(NotAImageException.class, () -> {
            service.upload(mockFile);
        });

        assertEquals("the provided file is not a image", exception.getMessage());
        assertEquals(ErrorCode.NOT_A_IMAGE, exception.getCode());
    }

}