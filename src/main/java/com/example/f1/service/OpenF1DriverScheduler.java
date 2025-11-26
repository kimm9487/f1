package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OpenF1DriverScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1DriverScheduler.class);

    private final OpenF1DriverService driverService;
    private final Integer defaultDriverNumber;
    private final Long defaultMeetingKey;

    public OpenF1DriverScheduler(
            OpenF1DriverService driverService,
            @Value("${openf1.driver.default-driver-number:55}") Integer defaultDriverNumber,
            @Value("${openf1.driver.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.driverService = driverService;
        this.defaultDriverNumber = defaultDriverNumber;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @Scheduled(fixedDelayString = "${openf1.driver.refresh-ms:300000}")
    public void refreshDrivers() {
        logger.info("Scheduled OpenF1 drivers refresh for meetingKey={} driver={}", defaultMeetingKey, defaultDriverNumber);
        driverService.fetchAndSave(defaultDriverNumber, defaultMeetingKey, null);
    }
}

