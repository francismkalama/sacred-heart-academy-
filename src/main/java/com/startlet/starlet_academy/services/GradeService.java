package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Grade;
import com.startlet.starlet_academy.models.Student;
import com.startlet.starlet_academy.models.Subject;
import com.startlet.starlet_academy.repositorys.GradeRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
import com.startlet.starlet_academy.repositorys.SubjectRepository;
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
