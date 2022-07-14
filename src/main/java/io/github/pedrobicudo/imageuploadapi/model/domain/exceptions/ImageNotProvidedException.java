package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageNotProvidedException extends DomainException {

    public ImageNotProvidedException() {
        super("image not provided", ErrorCode.IMAGE_NOT_PROVIDED);
    }
}
