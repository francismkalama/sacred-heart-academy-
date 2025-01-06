package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Parent;
import com.sacred.sacredheartacademy.models.Student;
import com.sacred.sacredheartacademy.repositorys.ParentRepository;
import com.sacred.sacredheartacademy.repositorys.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService {
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Transactional
    public Parent addParent(long studentId, String name, String relationship, String phone, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Parent parent = new Parent();
        parent.setStudent(student); // Link to the student
        parent.setName(name);
        parent.setRelationship(relationship);
        parent.setPhone(phone);
        parent.setEmail(email);
        // Save the parent
        return parentRepository.save(parent);
    }
}
