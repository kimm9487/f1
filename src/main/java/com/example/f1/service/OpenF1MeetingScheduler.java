package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "openf1.meetings.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class OpenF1MeetingScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1MeetingScheduler.class);
    
    @Autowired
    private OpenF1MeetingService meetingService;
    
    @Scheduled(fixedRateString = "${openf1.meetings.refresh-ms:3600000}") // 1시간 기본값
    public void scheduledMeetingSync() {
        logger.info("Starting scheduled meeting data sync from OpenF1");
        try {
            meetingService.fetchAndSaveMeetings();
            logger.info("Completed scheduled meeting data sync");
        } catch (Exception e) {
            logger.error("Error during scheduled meeting sync: {}", e.getMessage(), e);
        }
    }
}
