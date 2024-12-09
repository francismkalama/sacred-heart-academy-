package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Institution.Teacher;
import com.startlet.starlet_academy.repositorys.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId);
    }
}
