package com.sacred.sacredheartacademy.repositorys;


import com.sacred.sacredheartacademy.models.Institution.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
