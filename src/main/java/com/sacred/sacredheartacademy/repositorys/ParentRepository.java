package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Institution.Payment;
import com.sacred.sacredheartacademy.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query(value = "SELECT * FROM Payment p WHERE p.fee_id = :feeId",nativeQuery = true)
    List<Payment> findByFeeFeeId(int feeId);

}
