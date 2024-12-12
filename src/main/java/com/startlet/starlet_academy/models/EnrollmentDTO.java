package com.startlet.starlet_academy.models;

import lombok.Data;

import java.util.Date;

@Data
public class EnrollmentDTO {
    private String className;
    private String section;
    private Date enrollmentDate;
    private String status;
}
