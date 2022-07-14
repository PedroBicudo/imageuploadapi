package io.github.pedrobicudo.imageuploadapi.model.domain.exceptions;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomainException extends RuntimeException {
    private final ErrorCode code;

    public DomainException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}
