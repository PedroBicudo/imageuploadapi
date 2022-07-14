package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidImageException extends DomainException {

    public InvalidImageException() {
        super("the provided image is invalid", ErrorCode.INVALID_IMAGE);
    }
}
