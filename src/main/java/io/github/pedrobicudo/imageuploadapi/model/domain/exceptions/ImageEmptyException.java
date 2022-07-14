package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageEmptyException extends DomainException {

    public ImageEmptyException() {
        super("image is empty", ErrorCode.IMAGE_EMPTY);
    }
}
