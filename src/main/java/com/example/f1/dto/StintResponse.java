package com.example.f1.dto;

import java.time.LocalDateTime;

public class StintResponse {
    
    private Long id;
    private LocalDateTime dateCreated;
    private String compound;
    private Integer driverNumber;
    private Integer lapEnd;
    private Integer lapStart;
    private Integer meetingKey;
    private Integer sessionKey;
    private Integer stintNumber;
    private Integer tyreAgeAtStart;
    
    // Constructors
    public StintResponse() {}
    
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
