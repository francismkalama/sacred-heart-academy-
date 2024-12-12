package com.startlet.starlet_academy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startlet.starlet_academy.models.Institution.Fee;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Students")
@Getter
@Setter
//@AllArgsConstructors
//@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long Id;
//    private String admNo;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
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
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parent> parents;
    @JsonIgnore
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fee> fees;
//    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Grade> grades;
//    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
//    private HealthRecord healthRecord;
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
//    private List<EmergencyContact> emergencyContacts;
}
