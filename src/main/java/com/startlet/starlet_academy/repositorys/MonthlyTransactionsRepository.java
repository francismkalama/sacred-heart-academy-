package com.startlet.starlet_academy.repositorys;

import com.startlet.starlet_academy.models.Institution.Fees;
import com.startlet.starlet_academy.models.dataobjects.MonthlyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyTransactionsRepository extends JpaRepository<MonthlyTransactions, String> {

}
