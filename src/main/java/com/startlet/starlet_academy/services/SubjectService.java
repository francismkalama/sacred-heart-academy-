package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Subject;
import com.startlet.starlet_academy.repositorys.SubjectRepository;
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
