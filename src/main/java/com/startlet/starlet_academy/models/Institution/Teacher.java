package com.startlet.starlet_academy.models.Institution;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(mappedBy = "classTeacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SacredClass assignedClass;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;
}
