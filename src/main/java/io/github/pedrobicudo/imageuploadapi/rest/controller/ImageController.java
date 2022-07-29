package io.github.pedrobicudo.imageuploadapi.rest.controller;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.github.pedrobicudo.imageuploadapi.model.domain.repositories.ImageRepository;
import io.github.pedrobicudo.imageuploadapi.model.domain.services.interfaces.IImageService;
import io.github.pedrobicudo.imageuploadapi.rest.ApiError;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImageDTO;
import io.github.pedrobicudo.imageuploadapi.rest.dto.ImagePath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ImagePath.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"name\": \"f6e551e0-b69a-401f-8ee2-99bfbbe2c571\",\n" +
                                    "\"location\": \"/images/photo-f6e551e0-b69a-401f-8ee2-99bfbbe2c571\"\n" +
                                    "}\n"
                            )
                    })}
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
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
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"code\": \"IMAGE_TOO_BIG\"," +
                                    "\n\"message\": \"the image size must have at most 5MB\"\n" +
                                    "}\n"
                            )
                    })}
            )
    })
    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ImagePath upload(
            @RequestBody MultipartFile file
    ) {
        return service.upload(file);
    }

    @Operation(description = "get image")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "image found",
                    content = {
                            @Content(mediaType = MediaType.IMAGE_PNG_VALUE),
                            @Content(mediaType = MediaType.IMAGE_JPEG_VALUE),
                            @Content(mediaType = MediaType.IMAGE_GIF_VALUE),
                            @Content(mediaType = MediaType.APPLICATION_XML_VALUE)
                    }
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                implementation = ApiError.class), examples = {
                                @ExampleObject(value = "{\n" +
                                        "\"code\": \"IMAGE_EMPTY\",\n" +
                                        "\"message\": \"image is empty\"\n" +
                                        "}\n"
                                )
                    })}
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                            implementation = ApiError.class), examples = {
                            @ExampleObject(value = "{\n" +
                                    "\"code\": \"IMAGE_NOT_FOUND\",\n" +
                                    "\"message\": \"image not found\"\n" +
                                    "}\n"
                            )
                    })}
            )
    })
    @GetMapping(value = "/photo-{id}", produces = {
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Resource> findById(
            @PathVariable("id") UUID id
    ) {
        ImageDTO image = service.findById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition
                .inline()
                .filename("photo-"+id.toString())
                .build().toString()
        );
        headers.add(HttpHeaders.CONTENT_TYPE, image.getMediaType().toString());
        headers.add(HttpHeaders.CONTENT_LENGTH, ""+image.getContent().length);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingPathVariableException(MissingPathVariableException e) {
        return new ApiError(ErrorCode.VALID_IMAGE_ID_NOT_PROVIDED, "valid image id not provided");
    }
}
