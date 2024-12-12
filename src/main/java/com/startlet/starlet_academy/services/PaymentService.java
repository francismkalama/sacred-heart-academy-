package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Institution.Fee;
import com.startlet.starlet_academy.models.Institution.Payment;
import com.startlet.starlet_academy.repositorys.FeeRepository;
import com.startlet.starlet_academy.repositorys.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FeeRepository feeRepository;

    public Payment addPayment(long feeId, Payment payment) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found"));

        payment.setFee(fee);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByFeeId(int feeId) {
        return paymentRepository.findByFeeFeeId(feeId);
    }

}
