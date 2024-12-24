package com.sacred.sacredheartacademy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Subjects")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "name")
    private String name;

    // Getters and Setters
}
