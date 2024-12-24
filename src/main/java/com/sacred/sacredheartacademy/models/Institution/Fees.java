package com.sacred.sacredheartacademy.models.Institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sacred.sacredheartacademy.models.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private BigDecimal outstandingFees = BigDecimal.ZERO;;
    private BigDecimal admission = BigDecimal.ZERO;;
    private BigDecimal feesAmount  = BigDecimal.ZERO;
    private BigDecimal tution = BigDecimal.ZERO;;
    private BigDecimal lunch = BigDecimal.ZERO;;
    private BigDecimal transport = BigDecimal.ZERO;;
    private BigDecimal computer = BigDecimal.ZERO;;
    private BigDecimal exams = BigDecimal.ZERO;;
    private BigDecimal assessment = BigDecimal.ZERO;;
    private BigDecimal extraCurriculum = BigDecimal.ZERO;;
    private BigDecimal total = BigDecimal.ZERO;;
    private LocalDateTime dateSaved;
    private LocalDateTime updatedDate;
//    private BigDecimal balance;
//    private LocalDate dueDate;

}
