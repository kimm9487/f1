package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.stints.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1StintScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1StintScheduler.class);
    
    @Autowired
    private OpenF1StintService stintService;
    
    @Scheduled(fixedRateString = "${openf1.stints.refresh-ms:600000}") // 10분 기본값
    public void scheduledStintSync() {
        logger.info("Starting scheduled stint data sync from OpenF1");
        try {
            stintService.fetchAndSaveStints();
            logger.info("Completed scheduled stint data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled stint sync: {}", e.getMessage(), e);
        }
    }
}
