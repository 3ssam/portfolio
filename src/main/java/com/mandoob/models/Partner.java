package com.mandoob.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "This field is required field")
    private String name;

    @Column(length = 3000)
    private String bio;

    @Column(name = "imageName", length = 3000)
    private String imageName;

    @Column(name = "imageType", length = 3000)
    private String imagetype;

    @Lob
    @Column(name = "imageByte", length = 3000)
    private byte[] imageByte;

    private String imageSource;
}
