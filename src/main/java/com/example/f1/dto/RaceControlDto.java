package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceControlDto {
    
    @JsonProperty("category")
    private String category;
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("flag")
    private String flag;
    
    @JsonProperty("lap_number")
    private Integer lapNumber;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("scope")
    private String scope;
    
    @JsonProperty("sector")
    private Integer sector;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    // Constructors
    public RaceControlDto() {}
    
    // Getters and Setters
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
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
    
    public String getFlag() {
        return flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public Integer getSector() {
        return sector;
    }
    
    public void setSector(Integer sector) {
        this.sector = sector;
    }
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
}
