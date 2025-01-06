package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Institution.Course;
import com.sacred.sacredheartacademy.models.Institution.SacredClass;
import com.sacred.sacredheartacademy.models.Institution.Teacher;
import com.sacred.sacredheartacademy.repositorys.CourseRepository;
import com.sacred.sacredheartacademy.repositorys.SacredClassRepository;
import com.sacred.sacredheartacademy.repositorys.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SacredClassRepository sacredClassRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Teacher updateTeacher(int teacherId, Teacher updatedTeacher) {
        // Fetch the teacher from the database
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Update basic details
        existingTeacher.setFirstName(updatedTeacher.getFirstName());
        existingTeacher.setLastName(updatedTeacher.getLastName());
        existingTeacher.setEmail(updatedTeacher.getEmail());
        existingTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());

        // Update associated classes (if any)
        if (updatedTeacher.getAssignedClasses() != null) {
            List<SacredClass> assignedClasses = updatedTeacher.getAssignedClasses().stream()
                    .map(cls -> sacredClassRepository.findById(cls.getClassId())
                            .orElseThrow(() -> new RuntimeException("Class not found: " + cls.getClassId())))
                    .collect(Collectors.toList());
            existingTeacher.setAssignedClasses(assignedClasses);
        }

        // Update associated courses (if any)
        if (updatedTeacher.getCoursesTaught() != null) {
            List<Course> coursesTaught = updatedTeacher.getCoursesTaught().stream()
                    .map(course -> courseRepository.findById(course.getCourseId())
                            .orElseThrow(() -> new RuntimeException("Course not found: " + course.getCourseId())))
                    .collect(Collectors.toList());
            existingTeacher.setCoursesTaught(coursesTaught);
        }

        // Save the updated teacher
        return teacherRepository.save(existingTeacher);
    }
}
