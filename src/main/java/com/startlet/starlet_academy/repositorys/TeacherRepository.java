package com.startlet.starlet_academy.repositorys;


import com.startlet.starlet_academy.models.Institution.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
