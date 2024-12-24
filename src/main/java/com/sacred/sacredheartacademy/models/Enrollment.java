package com.sacred.sacredheartacademy.models;

import com.sacred.sacredheartacademy.models.Institution.SacredClass;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Enrollments")
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private int enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SacredClass classEntity;

    @Column(name = "class")
    private String className;

    @Column(name = "section")
    private String section;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "status")
    private String status;

    // Getters and Setters
}
