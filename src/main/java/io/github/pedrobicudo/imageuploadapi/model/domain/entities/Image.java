package io.github.pedrobicudo.imageuploadapi.model.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CONTENT", nullable = false)
    private Byte[] content = null;

    @Column(name = "MEDIA_TYPE", nullable = false)
    private String mediaType;

}
