package io.github.pedrobicudo.imageuploadapi.model.domain.services.implementations;

import io.github.pedrobicudo.imageuploadapi.model.domain.entities.Image;
import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.*;
import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("id non existent must throw ImageNotFoundException")
    public void testIdNonExistentMustThrowImageNotFoundException() {
        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        UUID id = UUID.fromString("f6e551e0-b69a-401f-8ee2-99bfbbe2c571");

        DomainException exception = assertThrows(ImageNotFoundException.class, () -> {
            service.findById(id);
        });

        assertEquals("image not found", exception.getMessage());
        assertEquals(ErrorCode.IMAGE_NOT_FOUND, exception.getCode());

    }

    @Test
    @DisplayName("image dto must be returned successfully")
    public void testImageDTOMustBeReturnedSuccessfully() {
        UUID id = UUID.fromString("f6e551e0-b69a-401f-8ee2-99bfbbe2c571");
        Byte[] content = {0x1, 0x2, 0x3};
        String mediaType = "image/png";
        Image image = new Image(id, content, mediaType, new Date());

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(image));

        ImageDTO dtoExpected = new ImageDTO(MediaType.IMAGE_PNG, new byte[] {0x1, 0x2, 0x3});
        ImageDTO dtoGot = service.findById(id);

        assertEquals(dtoExpected.getMediaType(), dtoGot.getMediaType());
        assertArrayEquals(dtoExpected.getContent(), dtoGot.getContent());

    }

}