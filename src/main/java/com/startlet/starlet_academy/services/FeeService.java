package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.enums.FeeStatus;
import com.startlet.starlet_academy.models.Institution.Fee;
import com.startlet.starlet_academy.models.Institution.Payment;
import com.startlet.starlet_academy.models.Student;
import com.startlet.starlet_academy.repositorys.FeeRepository;
import com.startlet.starlet_academy.repositorys.PaymentRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeeService {
private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public Fee addFee(Fee fee) {
        return feeRepository.save(fee);
    }
    public List<Fee> getFeesByStudentId(long studentId) {
        return feeRepository.findByStudentStudentId(studentId);
    }
    public Fee createFee(long studentId, Fee fee) {
        // Fetch the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Link the fee to the student
        fee.setStudent(student);
        fee.setStatus(FeeStatus.UNPAID);
        return feeRepository.save(fee);
    }

    public List<Fee> getFeesByStudentId(int studentId) {
        return feeRepository.findByStudentStudentId(studentId);
    }
    public Payment addPayment(long feeId, BigDecimal paymentAmount, Payment paymentData) {
        log.info("%payment amount {}",paymentAmount);
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found"));
        // Update paid amount and create payment record
        BigDecimal updatedPaidAmount = fee.getPaidAmount().add(paymentAmount);

        Payment payment = new Payment();
        payment.setFee(fee);
        payment.setPaymentAmount(paymentAmount);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMode(paymentData.getPaymentMode());
        payment.setPaymentReference(paymentData.getPaymentReference());
        payment.setPaymentFor(paymentData.getPaymentFor());
        paymentRepository.save(payment);

        // Update fee
        fee.setPaidAmount(updatedPaidAmount);

        if (updatedPaidAmount.compareTo(fee.getFeeAmount()) >= 0) {
            fee.setStatus(FeeStatus.PAID);
        } else {
            fee.setStatus(FeeStatus.PARTIAL);
        }
        feeRepository.save(fee);
        return payment;
    }

    public List<Payment> getPaymentsByFeeId(int feeId) {
        return paymentRepository.findByFeeFeeId(feeId);
    }

    public Fee updateFee(long feeId, Fee updatedFee) {
        String paymentDesc= null;
        // Find the existing fee record
        Fee existingFee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee not found for ID: " + feeId));
        //compare fees
            if(existingFee.getPaidAmount().compareTo(BigDecimal.valueOf(0)) == 0){
                existingFee.setStatus(FeeStatus.UNPAID);
                paymentDesc = String.valueOf(FeeStatus.UNPAID).substring(0,1).toUpperCase()+String.valueOf(FeeStatus.UNPAID).substring(1).toLowerCase()+" payment";
                existingFee.setFeeDescription(paymentDesc);
        }else if(existingFee.getPaidAmount().compareTo(updatedFee.getFeeAmount()) <0){
                existingFee.setStatus(FeeStatus.PARTIAL);
                paymentDesc = String.valueOf(FeeStatus.PARTIAL).substring(0,1).toUpperCase()+String.valueOf(FeeStatus.PARTIAL).substring(1).toLowerCase()+" payment";
                existingFee.setFeeDescription(paymentDesc);
        }else{
                existingFee.setStatus(FeeStatus.PAID);
                paymentDesc = String.valueOf(FeeStatus.PAID).substring(0,1).toUpperCase()+String.valueOf(FeeStatus.PAID).substring(1).toLowerCase()+" payment";
                existingFee.setFeeDescription(paymentDesc);
            }
        existingFee.setFeeAmount(updatedFee.getFeeAmount());
        existingFee.setDueDate(updatedFee.getDueDate());
        // Update other details if needed (optional)
        if (updatedFee.getFeeDescription() != null) {
            existingFee.setFeeDescription(updatedFee.getFeeDescription());
        }
        // Save the updated fee
        return feeRepository.save(existingFee);
    }
}
