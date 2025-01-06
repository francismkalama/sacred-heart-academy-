package com.sacred.sacredheartacademy.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
//    @NotBlank
    private String name;
    private String role;
}
