package com.startlet.starlet_academy.models;

import lombok.Data;

@Data
//@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String role;
    private String name;
    private boolean profileStatus;
}
