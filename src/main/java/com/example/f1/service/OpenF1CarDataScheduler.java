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

    public OpenF1CarDataScheduler(OpenF1CarDataService carDataService,
                                  @Value("${openf1.cardata.default-driver-number:55}") Integer driverNumber,
                                  @Value("${openf1.cardata.default-meeting-key:1208}") Long meetingKey) {
        this.carDataService = carDataService;
        this.driverNumber = driverNumber;
        this.meetingKey = meetingKey;
    }

    @Scheduled(fixedDelayString = "${openf1.cardata.refresh-ms:60000}")
    public void refreshCarData() {
        logger.info("Scheduled OpenF1 car_data refresh for meetingKey={} driver={}", meetingKey, driverNumber);
        carDataService.fetchAndSave(driverNumber, meetingKey, null);
    }
}

