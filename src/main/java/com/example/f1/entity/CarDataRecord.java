package com.example.f1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_data_records")
public class CarDataRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_data_record_id")
    private Long id;

    @Column(name = "meeting_key", nullable = false)
    private Long meetingKey;

    @Column(name = "session_key")
    private Long sessionKey;

    @Column(name = "driver_number", nullable = false)
    private Integer driverNumber;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "throttle")
    private Integer throttle;

    @Column(name = "brake")
    private Integer brake;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "drs")
    private Integer drs;

    @Column(name = "gear")
    private Integer gear;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @PrePersist
    public void prePersist() {
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}

