package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Enrollment;
import com.sacred.sacredheartacademy.models.Student;
import com.sacred.sacredheartacademy.repositorys.EnrollmentRepository;
import com.sacred.sacredheartacademy.repositorys.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Enrollment addEnrollment(long studentId, String className, String section, Date enrollmentDate, String status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassName(className);
        enrollment.setSection(section);
        enrollment.setEnrollmentDate(enrollmentDate);
        enrollment.setStatus(status);

        // Save the enrollment
        return enrollmentRepository.save(enrollment);
    }
}
