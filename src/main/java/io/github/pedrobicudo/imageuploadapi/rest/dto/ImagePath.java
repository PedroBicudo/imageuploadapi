package io.github.pedrobicudo.imageuploadapi.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagePath {

    @JsonIgnore
    private UUID imageId;

    @JsonProperty("location")
    public String location() {
        return "/images/photo-"+name();
    }

    @JsonProperty("name")
    public String name() {
        return imageId.toString();
    }

}
