package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartingGridDto {
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("time")
    private Double time;
    
    // Constructors
    public StartingGridDto() {}
    
    // Getters and Setters
    public Integer getDriverNumber() {
        return driverNumber;
    }
    
    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
    
    public Integer getMeetingKey() {
        return meetingKey;
    }
    
    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
    
    public Integer getPosition() {
        return position;
    }
    
    public void setPosition(Integer position) {
        this.position = position;
    }
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    public Double getTime() {
        return time;
    }
    
    public void setTime(Double time) {
        this.time = time;
    }
}
