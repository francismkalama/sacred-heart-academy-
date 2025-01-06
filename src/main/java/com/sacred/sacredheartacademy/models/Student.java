package com.sacred.sacredheartacademy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Students")
@Getter
@Setter
//@AllArgsConstructors
//@NoArgsConstructor
public class Student {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "student_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "student_gen_seq")
    @SequenceGenerator(name = "student_gen_seq", sequenceName = "student_gen_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "student_id")
    private Long Id;
    @Column(unique = true)
    private String admNo;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String studentClass;
    private String term;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private Date dateOfAdmission;
    @Column(name = "gender")
    private String gender;
    @Column(name = "address_street")
    private String addressStreet;
    @Column(name = "address_city")
    private String addressCity;
    @Column(name = "address_state")
    private String addressState;
    @Column(name = "address_postal_code")
    private String addressPostalCode;
    @JsonIgnore
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Parent> parents;
    @JsonIgnore
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Fees> fees;
    private LocalDateTime dateSaved;
    private LocalDateTime updatedDate;
//    private boolean student_status;
//    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Grade> grades;
//    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
//    private HealthRecord healthRecord;
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<EmergencyContact> emergencyContacts;
}
