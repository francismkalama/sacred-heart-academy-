package com.sacred.sacredheartacademy.models.Institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private BigDecimal paymentAmount;
    private LocalDate paymentDate;
    private String paymentMode;
    private String paymentFor;
    @Column(unique = true)
    private String paymentReference;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_id")
    @JsonIgnore
    private Fees fee;

}
