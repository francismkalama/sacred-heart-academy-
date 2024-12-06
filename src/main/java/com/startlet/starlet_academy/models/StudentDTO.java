package com.startlet.starlet_academy.models;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private String firstName;
    private String lastName;
    private long dateOfBirth;
    private String gender;
    private String addressStreet;
    private String addressCity;
    private String addressState;
    private String addressPostalCode;
    private List<ParentDTO> parents;
    private List<EnrollmentDTO> enrollments;
}
