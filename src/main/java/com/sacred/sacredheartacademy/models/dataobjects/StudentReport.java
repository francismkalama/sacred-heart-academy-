package com.sacred.sacredheartacademy.models.dataobjects;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StudentReport {

    private Date dateOfAdmission;

    private Date dateOfBirth;

    private String addressCity;

    private String addressPostalCode;

    private String addressState;

    private String addressStreet;

    private String admNo;

    private String firstName;

    private String gender;

    private String lastName;

    private String studentClass;

    private String term;

    private BigDecimal feeToBePaid;

    private BigDecimal admission;

    private BigDecimal assessment;

    private BigDecimal computer;

    private BigDecimal exams;

    private BigDecimal extra_curriculum;

    private BigDecimal fees_amount;

    private BigDecimal lunch;

    private BigDecimal outstanding_fees;

    private BigDecimal transport;

    private BigDecimal total;

    private BigDecimal tuition;
}
