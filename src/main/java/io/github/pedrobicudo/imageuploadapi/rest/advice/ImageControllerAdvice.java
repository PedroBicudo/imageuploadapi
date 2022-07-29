package io.github.pedrobicudo.imageuploadapi.rest.advice;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.github.pedrobicudo.imageuploadapi.model.domain.exceptions.*;
import io.github.pedrobicudo.imageuploadapi.rest.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleImageTooBigException() {
        return new ApiError(ErrorCode.IMAGE_TOO_BIG, "the image size must have at most 5MB");
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

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleImageNotFoundException(ImageNotFoundException e) {
        return new ApiError(e.getCode(), e.getMessage());
    }

}
