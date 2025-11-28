package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.session-result.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1SessionResultScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1SessionResultScheduler.class);
    
    @Autowired
    private OpenF1SessionResultService sessionResultService;
    
    @Scheduled(fixedRateString = "${openf1.session-result.refresh-ms:600000}") // 10분 기본값
    public void scheduledSessionResultSync() {
        logger.info("Starting scheduled session result data sync from OpenF1");
        try {
            sessionResultService.fetchAndSaveSessionResults();
            logger.info("Completed scheduled session result data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled session result sync: {}", e.getMessage(), e);
        }
    }
}
