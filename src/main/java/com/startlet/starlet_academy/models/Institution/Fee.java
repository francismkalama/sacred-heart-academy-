package com.startlet.starlet_academy.models.Institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startlet.starlet_academy.enums.FeeStatus;
import com.startlet.starlet_academy.models.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "fees_analytics")
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feeId;
    private String feeMonth;
    private String feeYear;
    private BigDecimal totalPaid;
    private BigDecimal totalOutstanding;
    private BigDecimal totalExpected;
    private LocalDateTime dateCreated;
}
