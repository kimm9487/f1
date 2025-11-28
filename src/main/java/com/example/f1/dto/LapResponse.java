package com.example.f1.dto;

import java.time.LocalDateTime;

public class LapResponse {
    
    private Long id;
    private LocalDateTime dateCreated;
    private Integer driverNumber;
    private Double durationSector1;
    private Double durationSector2;
    private Double durationSector3;
    private Integer i1Speed;
    private Integer i2Speed;
    private Boolean isPitOutLap;
    private Double lapDuration;
    private Integer lapNumber;
    private Integer meetingKey;
    private Integer sessionKey;
    private Integer stSpeed;
    
    // Constructors
    public LapResponse() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public Integer getDriverNumber() {
        return driverNumber;
    }
    
    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
    
    public Double getDurationSector1() {
        return durationSector1;
    }
    
    public void setDurationSector1(Double durationSector1) {
        this.durationSector1 = durationSector1;
    }
    
    public Double getDurationSector2() {
        return durationSector2;
    }
    
    public void setDurationSector2(Double durationSector2) {
        this.durationSector2 = durationSector2;
    }
    
    public Double getDurationSector3() {
        return durationSector3;
    }
    
    public void setDurationSector3(Double durationSector3) {
        this.durationSector3 = durationSector3;
    }
    
    public Integer getI1Speed() {
        return i1Speed;
    }
    
    public void setI1Speed(Integer i1Speed) {
        this.i1Speed = i1Speed;
    }
    
    public Integer getI2Speed() {
        return i2Speed;
    }
    
    public void setI2Speed(Integer i2Speed) {
        this.i2Speed = i2Speed;
    }
    
    public Boolean getIsPitOutLap() {
        return isPitOutLap;
    }
    
    public void setIsPitOutLap(Boolean isPitOutLap) {
        this.isPitOutLap = isPitOutLap;
    }
    
    public Double getLapDuration() {
        return lapDuration;
    }
    
    public void setLapDuration(Double lapDuration) {
        this.lapDuration = lapDuration;
    }
    
    public Integer getLapNumber() {
        return lapNumber;
    }
    
    public void setLapNumber(Integer lapNumber) {
        this.lapNumber = lapNumber;
    }
    
    public Integer getMeetingKey() {
        return meetingKey;
    }
    
    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    public Integer getStSpeed() {
        return stSpeed;
    }
    
    public void setStSpeed(Integer stSpeed) {
        this.stSpeed = stSpeed;
    }
}
