package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.location.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1LocationScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1LocationScheduler.class);
    
    @Autowired
    private OpenF1LocationService locationService;
    
    @Scheduled(fixedRateString = "${openf1.location.refresh-ms:120000}") // 2분 기본값
    public void scheduledLocationSync() {
        logger.info("Starting scheduled location data sync from OpenF1");
        try {
            locationService.fetchAndSaveLocations();
            logger.info("Completed scheduled location data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled location sync: {}", e.getMessage(), e);
        }
    }
}
