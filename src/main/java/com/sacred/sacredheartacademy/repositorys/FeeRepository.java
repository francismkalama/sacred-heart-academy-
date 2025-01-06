package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Institution.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fees, Long> {
@Query(value = "SELECT * FROM Fee f WHERE f.student_id = :studentId",nativeQuery = true)
    List<Fees> findByStudentStudentId(long studentId);
    @Query(value = "SELECT COALESCE(SUM(f.fees_Amount), 0) FROM fees_data f",nativeQuery = true)
    double sumFees();
    @Query(value = "SELECT COALESCE(SUM(f.total), 0) FROM fees_data f",nativeQuery = true)
    double sumPayments();
    @Query(value = "SELECT COALESCE(SUM(f.fees_Amount) - SUM(f.total),0) FROM fees_data f",nativeQuery = true)
    double sumOutstandingPayments();
}