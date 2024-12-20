package com.startlet.starlet_academy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean profile_status;
    private String name;
    @Column(nullable = false)
    private String user_role;
//    @Column(nullable = false)
//    private String name;
}