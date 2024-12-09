package com.startlet.starlet_academy.models.Institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "teacher_id")
    private int teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

//    @OneToOne(mappedBy = "classTeacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@ManyToMany
@JoinTable(
        name = "teacher_sacred_classes",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "class_id")
)
    @JsonIgnore
    private List<SacredClass> assignedClasses;

//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@ManyToMany
@JoinTable(
        name = "teacher_course",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
@JsonIgnore
    private List<Course> coursesTaught;
}
