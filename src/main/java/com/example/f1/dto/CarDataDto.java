package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class CarDataDto {

    @JsonProperty("meeting_key")
    private Long meetingKey;

    @JsonProperty("session_key")
    private Long sessionKey;

    @JsonProperty("driver_number")
    private Integer driverNumber;

    private OffsetDateTime date;

    private Integer speed;

    private Integer throttle;

    private Integer brake;

    private Integer rpm;

    private Integer drs;

    @JsonProperty("n_gear")
    private Integer gear;

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

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public void setThrottle(Integer throttle) {
        this.throttle = throttle;
    }

    public Integer getBrake() {
        return brake;
    }

    public void setBrake(Integer brake) {
        this.brake = brake;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Integer getDrs() {
        return drs;
    }

    public void setDrs(Integer drs) {
        this.drs = drs;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }
}

