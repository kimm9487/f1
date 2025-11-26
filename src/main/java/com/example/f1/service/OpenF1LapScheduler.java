package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.laps.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1LapScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1LapScheduler.class);
    
    @Autowired
    private OpenF1LapService lapService;
    
    @Scheduled(fixedRateString = "${openf1.laps.refresh-ms:300000}") // 5분 기본값
    public void scheduledLapSync() {
        logger.info("Starting scheduled lap data sync from OpenF1");
        try {
            lapService.fetchAndSaveLaps();
            logger.info("Completed scheduled lap data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled lap sync: {}", e.getMessage(), e);
        }
    }
}
