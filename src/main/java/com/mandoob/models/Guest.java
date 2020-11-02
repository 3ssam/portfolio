package com.mandoob.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "This field is required field")
    private String email;

    @Column(length = 3000)
    @NotBlank(message = "This field is required field")
    private String subject;

    @Column(length = 3000)
    @NotNull(message = "This field is required field")
    private String message;

    @NotBlank(message = "This field is required field")
    private String name;
}
