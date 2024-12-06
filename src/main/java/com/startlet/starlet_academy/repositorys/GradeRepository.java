package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}