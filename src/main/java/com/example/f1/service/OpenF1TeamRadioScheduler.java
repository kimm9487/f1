package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.team-radio.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1TeamRadioScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1TeamRadioScheduler.class);
    
    @Autowired
    private OpenF1TeamRadioService teamRadioService;
    
    @Scheduled(fixedRateString = "${openf1.team-radio.refresh-ms:300000}") // 5분 기본값
    public void scheduledTeamRadioSync() {
        logger.info("Starting scheduled team radio data sync from OpenF1");
        try {
            teamRadioService.fetchAndSaveTeamRadios();
            logger.info("Completed scheduled team radio data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled team radio sync: {}", e.getMessage(), e);
        }
    }
}
