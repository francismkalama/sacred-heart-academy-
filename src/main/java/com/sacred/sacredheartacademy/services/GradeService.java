package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Grade;
import com.sacred.sacredheartacademy.models.Student;
import com.sacred.sacredheartacademy.models.Subject;
import com.sacred.sacredheartacademy.repositorys.GradeRepository;
import com.sacred.sacredheartacademy.repositorys.StudentRepository;
import com.sacred.sacredheartacademy.repositorys.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional
    public Grade addGrade(long studentId, int subjectId, String term, int score) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setTerm(term);
        grade.setScore(score);

        // Save the grade
        return gradeRepository.save(grade);
    }
}
