//package com.sacred.sacredheartacademy.services;
//
//import com.sacred.sacredheartacademy.models.Institution.Fee;
//import com.sacred.sacredheartacademy.models.Institution.Fees;
//import com.sacred.sacredheartacademy.models.Institution.Payment;
//import com.sacred.sacredheartacademy.repositorys.FeeRepository;
//import com.sacred.sacredheartacademy.repositorys.PaymentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private FeeRepository feeRepository;
//
//    public Payment addPayment(long feeId, Payment payment) {
//        Fees fee = feeRepository.findById(feeId)
//                .orElseThrow(() -> new RuntimeException("Fee not found"));
//
//        payment.setFee(fee);
//        return paymentRepository.save(payment);
//    }
//
//    public List<Payment> getPaymentsByFeeId(int feeId) {
//        return paymentRepository.findByFeeFeeId(feeId);
//    }
//
//}
