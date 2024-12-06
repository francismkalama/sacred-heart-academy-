package com.startlet.starlet_academy.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Grades")
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @Column(name = "term")
    private String term;
    @Column(name = "score")
    private int score;
    // Getters and Setters
}
