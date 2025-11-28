package com.example.f1.dto;

import com.example.f1.entity.WeatherRecord;

import java.time.LocalDateTime;

public class WeatherResponse {

    private Long meetingKey;
    private Long sessionKey;
    private LocalDateTime recordedAt;
    private Double airTemperature;
    private Double trackTemperature;
    private Double windSpeed;
    private Integer windDirection;
    private Integer humidity;
    private Double pressure;
    private Double rainfall;

    public static WeatherResponse fromEntity(WeatherRecord record) {
        if (record == null) {
            return null;
        }

        WeatherResponse response = new WeatherResponse();
        response.meetingKey = record.getMeetingKey();
        response.sessionKey = record.getSessionKey();
        response.recordedAt = record.getRecordedAt();
        response.airTemperature = record.getAirTemperature();
        response.trackTemperature = record.getTrackTemperature();
        response.windSpeed = record.getWindSpeed();
        response.windDirection = record.getWindDirection();
        response.humidity = record.getHumidity();
        response.pressure = record.getPressure();
        response.rainfall = record.getRainfall();
        return response;
    }

    public Long getMeetingKey() {
        return meetingKey;
    }

    public Long getSessionKey() {
        return sessionKey;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public Double getTrackTemperature() {
        return trackTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getRainfall() {
        return rainfall;
    }
}

