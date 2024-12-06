package com.startlet.starlet_academy.models;

import lombok.Data;

@Data
public class EnrollmentDTO {
    private String className;
    private String section;
    private long enrollmentDate;
    private String status;
}
