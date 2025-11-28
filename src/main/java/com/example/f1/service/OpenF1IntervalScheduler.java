package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OpenF1IntervalScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1IntervalScheduler.class);

    private final OpenF1IntervalService intervalService;
    private final Integer defaultDriverNumber;
    private final Long defaultMeetingKey;

    public OpenF1IntervalScheduler(OpenF1IntervalService intervalService,
                                   @Value("${openf1.intervals.default-driver-number:55}") Integer defaultDriverNumber,
                                   @Value("${openf1.intervals.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.intervalService = intervalService;
        this.defaultDriverNumber = defaultDriverNumber;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @Scheduled(fixedDelayString = "${openf1.intervals.refresh-ms:300000}")
    public void refreshIntervals() {
        logger.info("Scheduled OpenF1 intervals refresh for meetingKey={} driver={}", defaultMeetingKey, defaultDriverNumber);
        intervalService.fetchAndSave(defaultDriverNumber, defaultMeetingKey, null);
    }
}

