package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Payment;
import com.startlet.starlet_academy.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query(value = "SELECT * FROM Payment p WHERE p.fee_id = :feeId",nativeQuery = true)
    List<Payment> findByFeeFeeId(int feeId);

}
