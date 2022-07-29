package io.github.pedrobicudo.imageuploadapi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private MediaType mediaType;
    private byte[] content;
}
