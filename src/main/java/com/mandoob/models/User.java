package com.mandoob.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "This field is required field")
    private String email;

    @NotBlank(message = "This field is required field")
    private String password;

    @NotNull(message = "This field is required field")
    private Boolean activated = true;

    @NotBlank(message = "This field is required field")
    private String name;
}
