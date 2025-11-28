package com.example.f1.dto;

import com.example.f1.entity.IntervalRecord;

import java.time.LocalDateTime;

public class IntervalResponse {

    private Long meetingKey;
    private Long sessionKey;
    private Integer driverNumber;
    private Integer lapNumber;
    private Integer intervalNumber;
    private String intervalType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double intervalDuration;
    private LocalDateTime recordedAt;

    public static IntervalResponse fromEntity(IntervalRecord record) {
        if (record == null) {
            return null;
        }

        IntervalResponse response = new IntervalResponse();
        response.meetingKey = record.getMeetingKey();
        response.sessionKey = record.getSessionKey();
        response.driverNumber = record.getDriverNumber();
        response.lapNumber = record.getLapNumber();
        response.intervalNumber = record.getIntervalNumber();
        response.intervalType = record.getIntervalType();
        response.startTime = record.getStartTime();
        response.endTime = record.getEndTime();
        response.intervalDuration = record.getIntervalDuration();
        response.recordedAt = record.getRecordedAt();
        return response;
    }

    public Long getMeetingKey() {
        return meetingKey;
    }

    public Long getSessionKey() {
        return sessionKey;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public Integer getLapNumber() {
        return lapNumber;
    }

    public Integer getIntervalNumber() {
        return intervalNumber;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Double getIntervalDuration() {
        return intervalDuration;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
}

