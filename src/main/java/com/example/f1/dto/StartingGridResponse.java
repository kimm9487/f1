package com.example.f1.dto;

import java.time.LocalDateTime;

public class StartingGridResponse {
    
    private Long id;
    private LocalDateTime dateCreated;
    private Integer driverNumber;
    private Integer meetingKey;
    private Integer position;
    private Integer sessionKey;
    private Double time;
    
    // Constructors
    public StartingGridResponse() {}
    
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
