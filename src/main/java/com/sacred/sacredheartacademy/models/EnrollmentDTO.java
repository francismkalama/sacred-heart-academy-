package com.sacred.sacredheartacademy.models;

import lombok.Data;

import java.util.Date;

@Data
public class EnrollmentDTO {
    private String className;
    private String section;
//    private long enrollmentDate;
    private Date enrollmentDate;
    private String status;
}