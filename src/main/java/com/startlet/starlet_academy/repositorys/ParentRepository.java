package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
}
