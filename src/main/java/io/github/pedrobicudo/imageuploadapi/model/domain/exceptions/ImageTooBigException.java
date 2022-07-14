package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageTooBigException extends DomainException {

    public ImageTooBigException() {
        super("the image size must have at most 5MB", ErrorCode.IMAGE_TOO_BIG);
    }
}
