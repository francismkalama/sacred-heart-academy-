//package com.sacred.sacredheartacademy.controllers;
//
//import com.sacred.sacredheartacademy.models.Institution.Payment;
//import com.sacred.sacredheartacademy.services.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/payments")
//public class PaymentController {
//
////    @Autowired
////    private PaymentService paymentService;
//
//    @PostMapping("/{feeId}")
//    public ResponseEntity<Payment> addPayment(@PathVariable int feeId, @RequestBody Payment payment) {
//        return ResponseEntity.ok(paymentService.addPayment(feeId, payment));
//    }
//
//    @GetMapping("/fee/{feeId}")
//    public ResponseEntity<List<Payment>> getPaymentsByFee(@PathVariable int feeId) {
//        return ResponseEntity.ok(paymentService.getPaymentsByFeeId(feeId));
//    }
//}
