package com.sacred.sacredheartacademy.repositorys;


import com.sacred.sacredheartacademy.models.Institution.FeesHistory;
import com.sacred.sacredheartacademy.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeesHistoryRepository extends JpaRepository<FeesHistory, Long> {

    List<FeesHistory> findByStudent(Student student);

    Page<FeesHistory> findByStudent_Id(Long Id, Pageable pageable);
}