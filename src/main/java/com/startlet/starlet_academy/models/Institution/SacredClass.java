package com.startlet.starlet_academy.models.Institution;

import com.startlet.starlet_academy.models.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "sacred_classes")
public class SacredClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private long classId;

    private String className;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher classTeacher;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

}
