package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Enrollment;
import com.startlet.starlet_academy.models.Student;
import com.startlet.starlet_academy.repositorys.EnrollmentRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
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
    public Enrollment addEnrollment(int studentId, String className, String section, Date enrollmentDate, String status) {
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
