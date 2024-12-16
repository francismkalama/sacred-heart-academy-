package com.startlet.starlet_academy.models.Institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startlet.starlet_academy.models.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Fees_data")
@Data
public class Fees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fees_id")
    private long feeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;
//    private String admissionNo;
    private BigDecimal outstandingFees;
    private BigDecimal admission;
    private BigDecimal feesAmount;
    private BigDecimal tution;
    private BigDecimal lunch;
    private BigDecimal transport;
    private BigDecimal computer;
    private BigDecimal exams;
    private BigDecimal assessment;
    private BigDecimal extraCurriculum;
    private BigDecimal total;
//    private BigDecimal balance;
//    private LocalDate dueDate;

}
