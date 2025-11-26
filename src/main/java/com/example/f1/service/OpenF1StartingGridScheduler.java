package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.starting-grid.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1StartingGridScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1StartingGridScheduler.class);
    
    @Autowired
    private OpenF1StartingGridService startingGridService;
    
    @Scheduled(fixedRateString = "${openf1.starting-grid.refresh-ms:1800000}") // 30분 기본값
    public void scheduledStartingGridSync() {
        logger.info("Starting scheduled starting grid data sync from OpenF1");
        try {
            startingGridService.fetchAndSaveStartingGrids();
            logger.info("Completed scheduled starting grid data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled starting grid sync: {}", e.getMessage(), e);
        }
    }
}
