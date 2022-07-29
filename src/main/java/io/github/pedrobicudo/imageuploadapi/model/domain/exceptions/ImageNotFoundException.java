package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageNotFoundException extends DomainException {
    public ImageNotFoundException() {
        super("image not found", ErrorCode.IMAGE_NOT_FOUND);
    }
}
