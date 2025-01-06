package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Institution.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeesAnalyticsRepository extends JpaRepository<Fee,Long> {
    @Query(value = "SELECT count(*) FROM fees_analytics WHERE fee_month = :month AND fee_year = :year ",nativeQuery = true)
    int checkEntryBasedOnMonthandYear(String year, String month);


}
