package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.position.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1PositionScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1PositionScheduler.class);
    
    @Autowired
    private OpenF1PositionService positionService;
    
    @Scheduled(fixedRateString = "${openf1.position.refresh-ms:90000}") // 1.5분 기본값
    public void scheduledPositionSync() {
        logger.info("Starting scheduled position data sync from OpenF1");
        try {
            positionService.fetchAndSavePositions();
            logger.info("Completed scheduled position data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled position sync: {}", e.getMessage(), e);
        }
    }
}
