package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OpenF1CarDataScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1CarDataScheduler.class);

    private final OpenF1CarDataService carDataService;
    private final Integer driverNumber;
    private final Long meetingKey;
    private final Long sessionKey;

    public OpenF1CarDataScheduler(OpenF1CarDataService carDataService,
                                  @Value("${openf1.cardata.default-driver-number:55}") Integer driverNumber,
                                  @Value("${openf1.cardata.default-meeting-key:1208}") Long meetingKey,
                                  @Value("${openf1.cardata.default-session-key:9158}") Long sessionKey) {
        this.carDataService = carDataService;
        this.driverNumber = driverNumber;
        this.meetingKey = meetingKey;
        this.sessionKey = sessionKey;
    }

    @Scheduled(fixedDelayString = "${openf1.cardata.refresh-ms:60000}")
    public void refreshCarData() {
        if (!Boolean.parseBoolean(System.getProperty("openf1.cardata.scheduler.enabled", "false"))) {
            logger.debug("Car data scheduler is disabled");
            return;
        }
        logger.info("Scheduled OpenF1 car_data refresh for meetingKey={} driver={} session={}", meetingKey, driverNumber, sessionKey);
        carDataService.fetchAndSave(driverNumber, meetingKey, sessionKey);
    }
}

