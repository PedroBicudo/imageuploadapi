package io.github.pedrobicudo.imageuploadapi.rest.controller;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.ImageEmptyException;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.ImageNotProvidedException;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.InvalidImageException;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.NotAImageException;
import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces.IImageService;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImagePath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ImageController.class})
class ImageControllerIntegrationTest {

    @MockBean
    private IImageService service;

    @MockBean
    private ImageRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final MultipartFile mockFile = Mockito.mock(MultipartFile.class);

    @Test
    @DisplayName("upload image must return status code created")
    public void testUploadImageMustReturnStatusCodeCreated() throws Exception {
        String id = "7adbb1e4-3edf-4b83-bc1e-76a0ab5bc0b2";
        Mockito.when(service.upload(Mockito.any()))
                .thenReturn(new ImagePath(UUID.fromString(id)));

        mockMvc.perform(post("/images")
                .contentType("multipart/form-data")
                .content(String.valueOf(new MockMultipartFile("file", "file.png", "image/*", new byte[100]))))
                .andExpect(status().isCreated())
                .andExpect(header().string("content-type", "application/json"))
                .andExpect(jsonPath("$.location").value("/images/photo-7adbb1e4-3edf-4b83-bc1e-76a0ab5bc0b2"))
                .andExpect(jsonPath("$.name").value("photo-7adbb1e4-3edf-4b83-bc1e-76a0ab5bc0b2"));

    }

    @Test
    @DisplayName("use upload without body must return status code Bad Request")
    public void testUploadWithoutBodyMustReturnStatusCodeBadRequest() throws Exception {
        Mockito.when(service.upload(Mockito.any()))
                .thenThrow(new ImageNotProvidedException());

        mockMvc.perform(post("/images")
                .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.IMAGE_NOT_PROVIDED.toString()))
                .andExpect(jsonPath("$.message").value("image not provided"));
    }

    @Test
    @DisplayName("use upload with empty image must return status code Bad Request")
    public void testUploadWithEmptyBodyMustReturnStatusCodeBadRequest() throws Exception {
        Mockito.when(service.upload(Mockito.any()))
                .thenThrow(new ImageEmptyException());

        mockMvc.perform(post("/images")
                .contentType("multipart/form-data")
                .content(new byte[0]))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.IMAGE_EMPTY.toString()))
                .andExpect(jsonPath("$.message").value("image is empty"));
    }

    @Test
    @DisplayName("use upload incorrect file which is not a image must return status code unprocessable entity")
    public void testUploadWithIncorrectFileWhichIsNotImageMustReturnStatusCodeUnprocessableEntity() throws Exception {
        Mockito.when(service.upload(Mockito.any()))
                .thenThrow(new NotAImageException());

        mockMvc.perform(post("/images")
                .contentType("multipart/form-data")
                .content(new byte[100000000]))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_A_IMAGE.toString()))
                .andExpect(jsonPath("$.message").value("the provided file is not a image"));
    }

    @Test
    @DisplayName("use upload invalid image must return status code unprocessable entity")
    public void testUploadInvalidImageMustReturnStatusCodeUnprocessableEntity() throws Exception {
        Mockito.when(service.upload(Mockito.any()))
                .thenThrow(new InvalidImageException());

        mockMvc.perform(post("/images")
                .contentType("multipart/form-data")
                .content(new byte[100000000]))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_IMAGE.toString()))
                .andExpect(jsonPath("$.message").value("the provided image is invalid"));
    }

}