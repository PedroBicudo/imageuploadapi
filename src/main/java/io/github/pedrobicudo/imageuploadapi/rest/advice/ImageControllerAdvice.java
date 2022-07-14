package io.github.pedrobicudo.imageuploadapi.rest.advice;

import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.*;
import io.github.pedrobicudo.imageuploadapi.rest.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageControllerAdvice {

    @ExceptionHandler(ImageNotProvidedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleImageNotProvidedException(ImageNotProvidedException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ImageEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleImageEmptyException(ImageEmptyException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ImageTooBigException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleImageTooBigException(ImageTooBigException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotAImageException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleNotAImageException(NotAImageException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(InvalidImageException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleInvalidImageException(InvalidImageException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

}
