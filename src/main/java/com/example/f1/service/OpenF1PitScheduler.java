package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.pit.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1PitScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1PitScheduler.class);
    
    @Autowired
    private OpenF1PitService pitService;
    
    @Scheduled(fixedRateString = "${openf1.pit.refresh-ms:240000}") // 4분 기본값
    public void scheduledPitSync() {
        logger.info("Starting scheduled pit data sync from OpenF1");
        try {
            pitService.fetchAndSavePits();
            logger.info("Completed scheduled pit data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled pit sync: {}", e.getMessage(), e);
        }
    }
}
