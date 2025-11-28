package com.example.f1.dto;

import java.time.LocalDateTime;

public class SessionResponse {
    
    private Long id;
    private LocalDateTime dateCreated;
    private Integer circuitKey;
    private String circuitShortName;
    private String countryCode;
    private Integer countryKey;
    private String countryName;
    private String dateEnd;
    private String dateStart;
    private String gmtOffset;
    private String location;
    private Integer meetingKey;
    private Integer sessionKey;
    private String sessionName;
    private String sessionType;
    private Integer year;
    
    // Constructors
    public SessionResponse() {}
    
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
    
    public Integer getCircuitKey() {
        return circuitKey;
    }
    
    public void setCircuitKey(Integer circuitKey) {
        this.circuitKey = circuitKey;
    }
    
    public String getCircuitShortName() {
        return circuitShortName;
    }
    
    public void setCircuitShortName(String circuitShortName) {
        this.circuitShortName = circuitShortName;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public Integer getCountryKey() {
        return countryKey;
    }
    
    public void setCountryKey(Integer countryKey) {
        this.countryKey = countryKey;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getDateEnd() {
        return dateEnd;
    }
    
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
    
    public String getDateStart() {
        return dateStart;
    }
    
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
    
    public String getGmtOffset() {
        return gmtOffset;
    }
    
    public void setGmtOffset(String gmtOffset) {
        this.gmtOffset = gmtOffset;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
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
    
    public String getSessionName() {
        return sessionName;
    }
    
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    
    public String getSessionType() {
        return sessionType;
    }
    
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
}
