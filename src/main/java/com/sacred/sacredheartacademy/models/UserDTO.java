package com.sacred.sacredheartacademy.models;

import lombok.Data;

@Data
//@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String role;
//    private String name;
    private boolean profileStatus;
}
