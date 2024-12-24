package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Institution.SacredClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SacredClassRepository extends JpaRepository<SacredClass, Long> {
}
