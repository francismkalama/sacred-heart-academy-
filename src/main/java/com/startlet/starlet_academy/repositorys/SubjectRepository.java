package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}