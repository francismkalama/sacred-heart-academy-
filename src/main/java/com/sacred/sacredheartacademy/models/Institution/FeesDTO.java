package com.sacred.sacredheartacademy.models.Institution;

import lombok.Data;


import java.math.BigDecimal;

@Data
public class FeesDTO {
//    private String admissionNo;
    private BigDecimal feesAmount;
    private BigDecimal outstandingFees;
    private BigDecimal admission;
    private BigDecimal tution;
    private BigDecimal lunch;
    private BigDecimal transport;
    private BigDecimal computer;
    private BigDecimal exams;
    private BigDecimal assessment;
    private BigDecimal extraCurriculum;
    private BigDecimal paidAmount;
    private String amountDesc;
}
