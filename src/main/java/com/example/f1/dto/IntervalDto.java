package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class IntervalDto {

    @JsonProperty("meeting_key")
    private Long meetingKey;

    @JsonProperty("session_key")
    private Long sessionKey;

    @JsonProperty("driver_number")
    private Integer driverNumber;

    @JsonProperty("lap_number")
    private Integer lapNumber;

    @JsonProperty("interval_number")
    private Integer intervalNumber;

    @JsonProperty("interval_type")
    private String intervalType;

    @JsonProperty("start_time")
    private OffsetDateTime startTime;

    @JsonProperty("end_time")
    private OffsetDateTime endTime;

    @JsonProperty("interval_duration")
    private Double intervalDuration;

    public Long getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Long meetingKey) {
        this.meetingKey = meetingKey;
    }

    public Long getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Long sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }

    public Integer getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(Integer lapNumber) {
        this.lapNumber = lapNumber;
    }

    public Integer getIntervalNumber() {
        return intervalNumber;
    }

    public void setIntervalNumber(Integer intervalNumber) {
        this.intervalNumber = intervalNumber;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getIntervalDuration() {
        return intervalDuration;
    }

    public void setIntervalDuration(Double intervalDuration) {
        this.intervalDuration = intervalDuration;
    }
}

