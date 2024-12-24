package com.sacred.sacredheartacademy.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Parents")
@Getter
@Setter
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private Long parentId;
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @Column(name = "name")
    private String name;
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email") //,unique = true
    private String email;
    // Getters and Setters
}
