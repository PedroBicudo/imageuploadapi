package io.github.pedrobicudo.imageuploadapi.rest.controller;

import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces.IImageService;
import io.github.pedrobicudo.imageuploadapi.rest.ApiError;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImagePath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "image controller")
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService service;

    @Autowired
    private ImageRepository repository;

    @Operation(description = "upload image")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "Image Uploaded",
                    content = {@Content(schema = @Schema(implementation = ImagePath.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"name\": \"photo-f6e551e0-b69a-401f-8ee2-99bfbbe2c571\",\n" +
                                    "\"location\": \"/images/photo-f6e551e0-b69a-401f-8ee2-99bfbbe2c571\"\n" +
                                    "}\n"
                            )
                    })}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(schema = @Schema(
                            implementation = ApiError.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"code\": \"IMAGE_EMPTY\",\n" +
                                    "\"message\": \"image is empty\"\n" +
                                    "}\n"
                            )
                    })}
            ),
            @ApiResponse(
                    responseCode = "422", description = "Unprocessable Entity",
                    content = {@Content(schema = @Schema(implementation = ApiError.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"code\": \"IMAGE_TOO_BIG\"," +
                                    "\n\"message\": \"the image size must have at most 5MB\"\n" +
                                    "}\n"
                            )
                    })}
            )
    })
    @PostMapping(consumes = {"multipart/form-data"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ImagePath upload(
            @RequestBody MultipartFile file
    ) {
        return service.upload(file);
    }

}
