package com.startlet.starlet_academy.models.dataobjects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table( name = "monthly_transactions")
@Entity
public class MonthlyTransactions {
    @Id
    private String month;
    private int year;
    private double totalExpectedAmount;
    private double totalOutstandingBalance;
    private double totalAmountPaid;
    // Constructors, Getters, and Setters
}