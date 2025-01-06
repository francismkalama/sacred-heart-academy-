package com.sacred.sacredheartacademy.models;

import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.models.Institution.FeesDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StudentDTO {
    private String firstName;
    private String lastName;
//    private long dateOfBirth;
    private String admNo;
    private Date dateOfBirth;
    private Date dateOfAdmission;
    private String gender;
    private String studentsClass;
    private String term;
    private String addressStreet;
    private String addressCity;
    private String addressState;
    private String addressPostalCode;
    private List<ParentDTO> parents;
    private List<FeesDTO> fees;
//    private List<Fees> fees;
    private List<EnrollmentDTO> enrollments;
}
