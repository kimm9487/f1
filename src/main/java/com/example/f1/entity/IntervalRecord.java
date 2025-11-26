package com.example.f1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interval_records")
public class IntervalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interval_record_id")
    private Long id;

    @Column(name = "meeting_key", nullable = false)
    private Long meetingKey;

    @Column(name = "session_key")
    private Long sessionKey;

    @Column(name = "driver_number")
    private Integer driverNumber;

    @Column(name = "lap_number")
    private Integer lapNumber;

    @Column(name = "interval_number")
    private Integer intervalNumber;

    @Column(name = "interval_type", length = 100)
    private String intervalType;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "interval_duration")
    private Double intervalDuration;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getIntervalDuration() {
        return intervalDuration;
    }

    public void setIntervalDuration(Double intervalDuration) {
        this.intervalDuration = intervalDuration;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}

