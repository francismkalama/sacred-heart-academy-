package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
//    List<Enrollment> findByClassEntityClassId(int classId);
    @Query(value = "SELECT * FROM Enrollment where student_id =: studentId",nativeQuery = true)
    List<Enrollment> findByStudentStudentId(int studentId);
}