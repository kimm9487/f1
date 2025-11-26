package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionDto {
    
    @JsonProperty("circuit_key")
    private Integer circuitKey;
    
    @JsonProperty("circuit_short_name")
    private String circuitShortName;
    
    @JsonProperty("country_code")
    private String countryCode;
    
    @JsonProperty("country_key")
    private Integer countryKey;
    
    @JsonProperty("country_name")
    private String countryName;
    
    @JsonProperty("date_end")
    private String dateEnd;
    
    @JsonProperty("date_start")
    private String dateStart;
    
    @JsonProperty("gmt_offset")
    private String gmtOffset;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("session_key")
    private Integer sessionKey;
    
    @JsonProperty("session_name")
    private String sessionName;
    
    @JsonProperty("session_type")
    private String sessionType;
    
    @JsonProperty("year")
    private Integer year;
    
    // Constructors
    public SessionDto() {}
    
    // Getters and Setters
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
