package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDto {
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("x")
    private Integer x;
    
    @JsonProperty("y")
    private Integer y;
    
    @JsonProperty("z")
    private Integer z;
    
    // Constructors
    public LocationDto() {}
    
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
    
    public Integer getX() {
        return x;
    }
    
    public void setX(Integer x) {
        this.x = x;
    }
    
    public Integer getY() {
        return y;
    }
    
    public void setY(Integer y) {
        this.y = y;
    }
    
    public Integer getZ() {
        return z;
    }
    
    public void setZ(Integer z) {
        this.z = z;
    }
}
