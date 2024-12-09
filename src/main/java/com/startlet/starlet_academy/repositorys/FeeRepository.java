package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {
@Query(value = "SELECT * FROM Fee f WHERE f.student_id = :studentId",nativeQuery = true)
    List<Fee> findByStudentStudentId(long studentId);
//    List<Fee> findByStudentsStudentId(long studentId);
}