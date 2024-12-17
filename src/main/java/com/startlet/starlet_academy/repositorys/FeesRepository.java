package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FeesRepository extends JpaRepository<Fees, Long> {
    @Override
    List<Fees> findAll();
//    @Query(value = "SELECT SUM(f.fees_Amount) FROM fees_data f WHERE FUNCTION('DATE_PART', 'year', f.date_saved) = :year AND FUNCTION('DATE_PART', 'month', f.date_saved) = :month",nativeQuery = true)
    @Query(value ="SELECT SUM(f.fees_Amount) FROM fees_data f WHERE EXTRACT(YEAR FROM f.date_saved) = :year AND EXTRACT(MONTH FROM f.date_saved) = :month", nativeQuery = true)
    BigDecimal getTotalFeesAmountByMonthAndYear(int year, int month);
    @Query(value ="SELECT SUM(f.outstanding_fees) FROM fees_data f WHERE EXTRACT(YEAR FROM f.date_saved) = :year AND EXTRACT(MONTH FROM f.date_saved) = :month", nativeQuery = true)
    BigDecimal getTotalOutstandingAmountByMonthAndYear(int year, int month);
    @Query(value ="SELECT SUM(f.total) FROM fees_data f WHERE EXTRACT(YEAR FROM f.date_saved) = :year AND EXTRACT(MONTH FROM f.date_saved) = :month", nativeQuery = true)
    BigDecimal getTotalPaidAmountByMonthAndYear(int year, int month);
}
