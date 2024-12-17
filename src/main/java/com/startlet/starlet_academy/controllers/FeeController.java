package com.startlet.starlet_academy.controllers;

import com.startlet.starlet_academy.services.FeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/fees")
public class FeeController {

    private final FeeService feeService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }
//    @PostMapping("/student/{studentId}")
//    public ResponseEntity<Fee> createFee(@PathVariable int studentId, @RequestBody Fee fee) {
//        Fee createdFee = feeService.createFee(studentId, fee);
//        return ResponseEntity.ok(createdFee);
//    }

//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<Fee>> getFeesByStudentId(@PathVariable int studentId) {
//        List<Fee> fees = feeService.getFeesByStudentId(studentId);
//        return ResponseEntity.ok(fees);
//    }

//    @PostMapping("/{feeId}/payment")
//    public ResponseEntity<Payment> addPayment(@PathVariable int feeId, @RequestBody Payment payment) {
//        log.info("Controller Payment Data {}",payment.getPaymentAmount());
//        Payment createdPayment = feeService.addPayment(feeId, payment.getPaymentAmount(),payment);
//        return ResponseEntity.ok(createdPayment);
//    }

//    @GetMapping("/{feeId}/payments")
//    public ResponseEntity<List<Payment>> getPaymentsByFeeId(@PathVariable int feeId) {
//        List<Payment> payments = feeService.getPaymentsByFeeId(feeId);
//        return ResponseEntity.ok(payments);
//    }
//    @PutMapping("/amend/{feeId}")
//    public ResponseEntity<Fee> updateFee(@PathVariable long feeId, @RequestBody Fee updatedFee) {
//        Fee fee = feeService.updateFee(feeId, updatedFee);
//        return ResponseEntity.ok(fee);
//    }
    @GetMapping("/total_fee_amount")
    public ResponseEntity<Double> getTotalFeeAmount() {
        double total = feeService.getFeeSum();
        return ResponseEntity.ok(total);
    }
    @GetMapping("/total_payed_amount")
    public ResponseEntity<Double> getTotalFeePayments() {
        double total = feeService.getPaymentsSum();
        return ResponseEntity.ok(total);
    }
    @GetMapping("/total_outstanding_amount")
    public ResponseEntity<Double> getTotalOutstandingPayments() {
        double total = feeService.getOutstandingSum();
        return ResponseEntity.ok(total);
    }

}
