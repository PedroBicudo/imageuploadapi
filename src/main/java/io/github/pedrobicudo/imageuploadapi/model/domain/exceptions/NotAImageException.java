package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotAImageException extends DomainException {

    public NotAImageException() {
        super("the provided file is not a image", ErrorCode.NOT_A_IMAGE);
    }
}
