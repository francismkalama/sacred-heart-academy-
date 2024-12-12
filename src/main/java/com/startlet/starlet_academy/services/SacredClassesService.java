package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Institution.SacredClass;
import com.startlet.starlet_academy.repositorys.SacredClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SacredClassesService {
    @Autowired
    private SacredClassRepository classRepository;

    public SacredClass addClass(SacredClass classEntity) {
        return classRepository.save(classEntity);
    }

    public List<SacredClass> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<SacredClass> getClassById(long classId) {
        return classRepository.findById(classId);
    }

}
