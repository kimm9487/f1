package com.example.f1.dto;

import com.example.f1.entity.CarDataRecord;

import java.time.LocalDateTime;

public class CarDataResponse {

    private Long meetingKey;
    private Long sessionKey;
    private Integer driverNumber;
    private LocalDateTime recordedAt;
    private Integer speed;
    private Integer throttle;
    private Integer brake;
    private Integer rpm;
    private Integer drs;
    private Integer gear;

    public static CarDataResponse fromEntity(CarDataRecord record) {
        if (record == null) {
            return null;
        }

        CarDataResponse response = new CarDataResponse();
        response.meetingKey = record.getMeetingKey();
        response.sessionKey = record.getSessionKey();
        response.driverNumber = record.getDriverNumber();
        response.recordedAt = record.getRecordedAt();
        response.speed = record.getSpeed();
        response.throttle = record.getThrottle();
        response.brake = record.getBrake();
        response.rpm = record.getRpm();
        response.drs = record.getDrs();
        response.gear = record.getGear();
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

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public Integer getBrake() {
        return brake;
    }

    public Integer getRpm() {
        return rpm;
    }

    public Integer getDrs() {
        return drs;
    }

    public Integer getGear() {
        return gear;
    }
}

