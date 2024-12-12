package com.startlet.starlet_academy.models;

import lombok.Data;

@Data
//@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String role;
    private boolean profileStatus;
}
