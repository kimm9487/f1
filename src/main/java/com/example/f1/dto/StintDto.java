package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StintDto {
    
    @JsonProperty("compound")
    private String compound;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("lap_end")
    private Integer lapEnd;
    
    @JsonProperty("lap_start")
    private Integer lapStart;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("stint_number")
    private Integer stintNumber;
    
    @JsonProperty("tyre_age_at_start")
    private Integer tyreAgeAtStart;
    
    // Constructors
    public StintDto() {}
    
    // Getters and Setters
    public String getCompound() {
        return compound;
    }
    
    public void setCompound(String compound) {
        this.compound = compound;
    }
    
    public Integer getDriverNumber() {
        return driverNumber;
    }
    
    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
    
    public Integer getLapEnd() {
        return lapEnd;
    }
    
    public void setLapEnd(Integer lapEnd) {
        this.lapEnd = lapEnd;
    }
    
    public Integer getLapStart() {
        return lapStart;
    }
    
    public void setLapStart(Integer lapStart) {
        this.lapStart = lapStart;
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
    
    public Integer getStintNumber() {
        return stintNumber;
    }
    
    public void setStintNumber(Integer stintNumber) {
        this.stintNumber = stintNumber;
    }
    
    public Integer getTyreAgeAtStart() {
        return tyreAgeAtStart;
    }
    
    public void setTyreAgeAtStart(Integer tyreAgeAtStart) {
        this.tyreAgeAtStart = tyreAgeAtStart;
    }
}
