package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Subject;
import com.sacred.sacredheartacademy.repositorys.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Transactional
    public Subject addSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);

        // Save the subject
        return subjectRepository.save(subject);
    }
}
