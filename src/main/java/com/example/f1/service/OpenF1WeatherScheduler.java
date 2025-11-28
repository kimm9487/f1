package com.example.f1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OpenF1WeatherScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1WeatherScheduler.class);

    private final OpenF1WeatherService weatherService;
    private final Long defaultMeetingKey;

    public OpenF1WeatherScheduler(
            OpenF1WeatherService weatherService,
            @Value("${openf1.weather.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.weatherService = weatherService;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @Scheduled(fixedDelayString = "${openf1.weather.refresh-ms:300000}")
    public void refreshWeather() {
        logger.info("Scheduled fetch for OpenF1 weather (meetingKey={})", defaultMeetingKey);
        weatherService.fetchAndSaveWeather(defaultMeetingKey);
    }
}

