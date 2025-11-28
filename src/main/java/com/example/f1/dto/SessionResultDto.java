package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionResultDto {
    
    @JsonProperty("driver_number")
    private Integer driverNumber;
    
    @JsonProperty("grid_position")
    private Integer gridPosition;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("points")
    private Integer points;
    
    @JsonProperty("position")
    private Integer position;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("time")
    private Double time;
    
    // Constructors
    public SessionResultDto() {}
    
    // Getters and Setters
    public Integer getDriverNumber() {
        return driverNumber;
    }
    
    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
    
    public Integer getGridPosition() {
        return gridPosition;
    }
    
    public void setGridPosition(Integer gridPosition) {
        this.gridPosition = gridPosition;
    }
    
    public Integer getMeetingKey() {
        return meetingKey;
    }
    
    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
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
