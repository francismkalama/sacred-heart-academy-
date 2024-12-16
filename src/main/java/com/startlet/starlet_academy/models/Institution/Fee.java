//package com.startlet.starlet_academy.models.Institution;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.startlet.starlet_academy.enums.FeeStatus;
//import com.startlet.starlet_academy.models.Student;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class Fee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long feeId;
//    private BigDecimal feeAmount;
//    private BigDecimal paidAmount = BigDecimal.ZERO;
//    private LocalDate dueDate;
//
//    @Enumerated(EnumType.STRING)
//    private FeeStatus status;
//    private String feeDescription;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id")
//    @JsonIgnore
//    private Student student;
//
//    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Payment> payments;
//
//    // Getters and Setters
//}
