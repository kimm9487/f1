package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.overtakes.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1OvertakeScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1OvertakeScheduler.class);
    
    @Autowired
    private OpenF1OvertakeService overtakeService;
    
    @Scheduled(fixedRateString = "${openf1.overtakes.refresh-ms:180000}") // 3분 기본값
    public void scheduledOvertakeSync() {
        logger.info("Starting scheduled overtake data sync from OpenF1");
        try {
            overtakeService.fetchAndSaveOvertakes();
            logger.info("Completed scheduled overtake data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled overtake sync: {}", e.getMessage(), e);
        }
    }
}
