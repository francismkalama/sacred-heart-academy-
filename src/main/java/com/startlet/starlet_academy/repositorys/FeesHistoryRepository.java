package com.startlet.starlet_academy.repositorys;


import com.startlet.starlet_academy.models.Institution.FeesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeesHistoryRepository extends JpaRepository<FeesHistory, Long> {

}