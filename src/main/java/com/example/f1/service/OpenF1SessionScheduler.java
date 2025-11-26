package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.sessions.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1SessionScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1SessionScheduler.class);
    
    @Autowired
    private OpenF1SessionService sessionService;
    
    @Scheduled(fixedRateString = "${openf1.sessions.refresh-ms:1800000}") // 30분 기본값
    public void scheduledSessionSync() {
        logger.info("Starting scheduled session data sync from OpenF1");
        try {
            sessionService.fetchAndSaveSessions();
            logger.info("Completed scheduled session data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled session sync: {}", e.getMessage(), e);
        }
    }
}
