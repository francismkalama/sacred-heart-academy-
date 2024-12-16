package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fees, Long> {
@Query(value = "SELECT * FROM Fee f WHERE f.student_id = :studentId",nativeQuery = true)
    List<Fees> findByStudentStudentId(long studentId);
    @Query(value = "SELECT COALESCE(SUM(f.fee_Amount), 0) FROM Fee f",nativeQuery = true)
    double sumFees();
    @Query(value = "SELECT COALESCE(SUM(f.paid_Amount), 0) FROM Fee f",nativeQuery = true)
    double sumPayments();
//    List<Fee> findByStudentsStudentId(long studentId);
}