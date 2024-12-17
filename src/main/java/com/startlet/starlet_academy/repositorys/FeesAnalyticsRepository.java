package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeesAnalyticsRepository extends JpaRepository<Fee,Long> {
}
