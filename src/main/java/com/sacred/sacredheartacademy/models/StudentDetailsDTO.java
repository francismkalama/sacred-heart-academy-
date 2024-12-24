package com.sacred.sacredheartacademy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDTO {
    private Long studentId;
    private String gender;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String addressPostalCode;
    private String addressState;
    private String addressStreet;
    private String addressCity;
    private String name;
    private String relationship;
    private String phone;
    private String email;
    private BigDecimal feesAmount;
    private BigDecimal outstandingFees;
    private BigDecimal admission;
    private BigDecimal tution;
    private BigDecimal lunch;
    private  BigDecimal transport;
    private BigDecimal computer;
    private BigDecimal exams;
    private BigDecimal assessment;
    private BigDecimal extraCurriculim;

//    private List<Parent> parents;
//    private List<Enrollment> enrollments;
//    private List<FeesDTO> fees;

}
