package com.sacred.sacredheartacademy.models.dataobjects;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class FeeDTO {
    private BigDecimal feeAmount;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String paymentReference;
    private String paymentMode;
}
