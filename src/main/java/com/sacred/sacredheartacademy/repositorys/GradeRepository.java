package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}