package com.sacred.sacredheartacademy.poji;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StudentPoji {

    @ExcelRow
    private int rowIndex;
    @ExcelCellName("Date_of_Admission")
    private Date dateOfAdmission;
    @ExcelCellName("Date_of_birth")
    private Date dateOfBirth;
    @ExcelCellName("address_city")
    private String addressCity;
    @ExcelCellName("Address_Postal_Code")
    private String addressPostalCode;
    @ExcelCellName("Address_State")
    private String addressState;
    @ExcelCellName("address_street")
    private String addressStreet;
    @ExcelCellName("Adm_No")
    private String admNo;
    @ExcelCellName("First_Name")
    private String firstName;
    @ExcelCellName("Gender")
    private String gender;
    @ExcelCellName("Last_Name")
    private String lastName;
    @ExcelCellName("Student_class")
    private String studentClass;
    @ExcelCellName("term")
    private String term;
    @ExcelCellName("Fee to be Paid")
    private BigDecimal feeToBePaid;
    @ExcelCellName("admission")
    private BigDecimal admission;
    @ExcelCellName("assessment")
    private BigDecimal assessment;
    @ExcelCellName("computer")
    private BigDecimal computer;
    @ExcelCellName("exams")
    private BigDecimal exams;
    @ExcelCellName("extra_curriculum")
    private BigDecimal extra_curriculum;
    @ExcelCellName("fees_amount")
    private BigDecimal fees_amount;
    @ExcelCellName("lunch")
    private BigDecimal lunch;
    @ExcelCellName("outstanding_fees")
    private BigDecimal outstanding_fees;
    @ExcelCellName("transport")
    private BigDecimal transport;
    @ExcelCellName("total")
    private BigDecimal total;
    @ExcelCellName("tuition")
    private BigDecimal tuition;


}
