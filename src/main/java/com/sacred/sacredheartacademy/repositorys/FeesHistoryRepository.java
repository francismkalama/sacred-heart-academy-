package com.sacred.sacredheartacademy.repositorys;


import com.sacred.sacredheartacademy.models.Institution.FeesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeesHistoryRepository extends JpaRepository<FeesHistory, Long> {

}