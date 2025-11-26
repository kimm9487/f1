package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.race-control.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1RaceControlScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1RaceControlScheduler.class);
    
    @Autowired
    private OpenF1RaceControlService raceControlService;
    
    @Scheduled(fixedRateString = "${openf1.race-control.refresh-ms:120000}") // 2분 기본값
    public void scheduledRaceControlSync() {
        logger.info("Starting scheduled race control data sync from OpenF1");
        try {
            raceControlService.fetchAndSaveRaceControls();
            logger.info("Completed scheduled race control data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled race control sync: {}", e.getMessage(), e);
        }
    }
}
