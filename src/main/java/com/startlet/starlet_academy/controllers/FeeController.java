package com.startlet.starlet_academy.controllers;

import com.google.gson.Gson;
import com.startlet.starlet_academy.enums.FeeStatus;
import com.startlet.starlet_academy.models.Institution.Fee;
import com.startlet.starlet_academy.models.Institution.Payment;
import com.startlet.starlet_academy.models.Student;
import com.startlet.starlet_academy.models.dataobjects.FeeDTO;
import com.startlet.starlet_academy.repositorys.FeeRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
import com.startlet.starlet_academy.services.FeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/fees")
public class FeeController {
    @Autowired
    private FeeService feeService;

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @PostMapping("/student/{studentId}")
    public ResponseEntity<Fee> createFee(@PathVariable int studentId, @RequestBody Fee fee) {
        Fee createdFee = feeService.createFee(studentId, fee);
        return ResponseEntity.ok(createdFee);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Fee>> getFeesByStudentId(@PathVariable int studentId) {
        List<Fee> fees = feeService.getFeesByStudentId(studentId);
        return ResponseEntity.ok(fees);
    }

    @PostMapping("/{feeId}/payment")
    public ResponseEntity<Payment> addPayment(@PathVariable int feeId, @RequestBody Payment payment) {
        log.info("Controller Payment Data {}",payment.getPaymentAmount());
        Payment createdPayment = feeService.addPayment(feeId, payment.getPaymentAmount(),payment);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("/{feeId}/payments")
    public ResponseEntity<List<Payment>> getPaymentsByFeeId(@PathVariable int feeId) {
        List<Payment> payments = feeService.getPaymentsByFeeId(feeId);
        return ResponseEntity.ok(payments);
    }
    @PutMapping("/amend/{feeId}")
    public ResponseEntity<Fee> updateFee(@PathVariable long feeId, @RequestBody Fee updatedFee) {
        Fee fee = feeService.updateFee(feeId, updatedFee);
        return ResponseEntity.ok(fee);
    }
}
