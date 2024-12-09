package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByTeacherTeacherId(int teacherId);
    List<Course> findByClassEntityClassId(int classId);
}