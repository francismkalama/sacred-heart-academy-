package com.sacred.sacredheartacademy.services;


import com.sacred.sacredheartacademy.models.Institution.Course;
import com.sacred.sacredheartacademy.repositorys.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByTeacherId(int teacherId) {
        return courseRepository.findByTeacherTeacherId(teacherId);
    }

    public List<Course> getCoursesByClassId(int classId) {
        return courseRepository.findByClassEntityClassId(classId);
    }
}
