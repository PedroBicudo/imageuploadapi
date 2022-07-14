package io.github.pedrobicudo.imageuploadapi.rest;

import io.github.pedrobicudo.imageuploadapi.model.domain.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Error response")
public class ApiError {

    @Schema(description = "Error code", implementation = ErrorCode.class)
    private ErrorCode code;

    @Schema(description = "Error message")
    private String message;
}
