package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.Institution.Fee;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.repositorys.FeesAnalyticsRepository;
import com.sacred.sacredheartacademy.repositorys.FeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class FeeCronJob {
//    @Scheduled(cron = "0 0 0 1 * ?")
//@Value("${schedule.cron}")
//private String cronExpression;
    private final FeesRepository feesRepository;
    private final FeesAnalyticsRepository feesAnalyticsRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FeeCronJob(FeesRepository feesRepository, FeesAnalyticsRepository feesAnalyticsRepository) {
        this.feesRepository = feesRepository;
        this.feesAnalyticsRepository = feesAnalyticsRepository;
    }

//    @Scheduled(cron = "0 0 0 1 * ?")
@Scheduled(cron = "${schedule.cron}")
    public void runFirstDayOfMonthJob() {
        // Your business logic here
        System.out.println("Running job on the first day of the month to get Fee data.");
        List<Fees> fes = feesRepository.findAll();
        Fee feeAnalytics = new Fee();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        logger.info("{} {}",currentDateTime.getYear(),currentDateTime.getMonthValue());
        BigDecimal totalFeeMonthlyAmount = feesRepository.getTotalFeesAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue());
//        logger.info("****{}",totalFeeMonthlyAmount);
        BigDecimal totalOutstandingMonthlyAmount = feesRepository.getTotalOutstandingAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue());
//    logger.info("****{}",totalOutstandingMonthlyAmount);
        BigDecimal totalPaidMonthlyAmount = feesRepository.getTotalPaidAmountByMonthAndYear(currentDateTime.getYear(),currentDateTime.getMonthValue());
//    logger.info("****{}",totalPaidMonthlyAmount);
        feeAnalytics.setFeeMonth(Month.of(currentDateTime.getMonthValue()).name());
        feeAnalytics.setFeeYear(String.valueOf(currentDateTime.getYear()));
        feeAnalytics.setTotalExpected(totalFeeMonthlyAmount);
        feeAnalytics.setTotalPaid(totalPaidMonthlyAmount);
        feeAnalytics.setTotalOutstanding(totalOutstandingMonthlyAmount);
        try{
           int entryCheck = feesAnalyticsRepository.checkEntryBasedOnMonthandYear(String.valueOf(currentDateTime.getYear()),Month.of(currentDateTime.getMonthValue()).name());
           if (entryCheck == 0 ){
               feesAnalyticsRepository.save(feeAnalytics);
               logger.info("Fees Analytics updated");
           }
        }catch (Exception e){
            logger.error("Error Saving Fee analytics {}",e.getMessage());
        }
    }
}
