package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LapDto {
    
    @JsonProperty("date_created")
    private String dateCreated;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("duration_sector_1")
    private Double durationSector1;
    
    @JsonProperty("duration_sector_2")
    private Double durationSector2;
    
    @JsonProperty("duration_sector_3")
    private Double durationSector3;
    
    @JsonProperty("i1_speed")
    private Integer i1Speed;
    
    @JsonProperty("i2_speed")
    private Integer i2Speed;
    
    @JsonProperty("is_pit_out_lap")
    private Boolean isPitOutLap;
    
    @JsonProperty("lap_duration")
    private Double lapDuration;
    
    @JsonProperty("lap_number")
    private Integer lapNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("st_speed")
    private Integer stSpeed;
    
    // Constructors
    public LapDto() {}
    
    // Getters and Setters
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
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
