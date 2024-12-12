package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.SacredClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SacredClassRepository extends JpaRepository<SacredClass, Long> {
}
