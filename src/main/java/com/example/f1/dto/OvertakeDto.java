package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OvertakeDto {
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    // Constructors
    public OvertakeDto() {}
    
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
}
