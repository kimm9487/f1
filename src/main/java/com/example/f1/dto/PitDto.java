package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PitDto {
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("duration")
    private Double duration;
    
    @JsonProperty("lap_number")
    private Integer lapNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("pit_duration")
    private Double pitDuration;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    // Constructors
    public PitDto() {}
    
    // Getters and Setters
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public Integer getDriverNumber() {
        return driverNumber;
    }
    
    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
    
    public Double getDuration() {
        return duration;
    }
    
    public void setDuration(Double duration) {
        this.duration = duration;
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
    
    public Double getPitDuration() {
        return pitDuration;
    }
    
    public void setPitDuration(Double pitDuration) {
        this.pitDuration = pitDuration;
    }
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
}
