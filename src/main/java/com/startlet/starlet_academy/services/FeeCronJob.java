package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.Institution.Fee;
import com.startlet.starlet_academy.models.Institution.Fees;
import com.startlet.starlet_academy.repositorys.FeesAnalyticsRepository;
import com.startlet.starlet_academy.repositorys.FeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class FeeCronJob {
//    @Scheduled(cron = "0 0 0 1 * ?")

    private final FeesRepository feesRepository;
    private final FeesAnalyticsRepository feesAnalyticsRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FeeCronJob(FeesRepository feesRepository, FeesAnalyticsRepository feesAnalyticsRepository) {
        this.feesRepository = feesRepository;
        this.feesAnalyticsRepository = feesAnalyticsRepository;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void runFirstDayOfMonthJob() {
        // Your business logic here
        System.out.println("Running job on the first day of the month to get Fee data.");
        List<Fees> fes = feesRepository.findAll();
        Fee feeAnalytics = new Fee();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        logger.info("{} {}",currentDateTime.getYear(),currentDateTime.getMonthValue());
        BigDecimal totalFeeMonthlyAmount = feesRepository.getTotalFeesAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue()-1);
        BigDecimal totalOutstandingMonthlyAmount = feesRepository.getTotalOutstandingAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue()-1);
        BigDecimal totalPaidMonthlyAmount = feesRepository.getTotalPaidAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue()-1);
        feeAnalytics.setFeeMonth(Month.of(currentDateTime.getMonthValue()).name());
        feeAnalytics.setFeeYear(currentDateTime.getYear());
        feeAnalytics.setTotalExpected(totalFeeMonthlyAmount);
        feeAnalytics.setTotalPaid(totalPaidMonthlyAmount);
        feeAnalytics.setTotalOutstanding(totalOutstandingMonthlyAmount);
        try{
            feesAnalyticsRepository.save(feeAnalytics);
        }catch (Exception e){
            logger.error("Error Saving Fee analytics {}",e.getMessage());
        }
    }
}