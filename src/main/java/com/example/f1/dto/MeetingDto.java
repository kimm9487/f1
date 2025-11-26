package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingDto {
    
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
    
    @JsonProperty("date_start")
    private String dateStart;
    
    @JsonProperty("gmt_offset")
    private String gmtOffset;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("meeting_key")
    private Integer meetingKey;
    
    @JsonProperty("meeting_name")
    private String meetingName;
    
    @JsonProperty("meeting_official_name")
    private String meetingOfficialName;
    
    @JsonProperty("year")
    private Integer year;
    
    // Constructors
    public MeetingDto() {}
    
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
    
    public String getMeetingName() {
        return meetingName;
    }
    
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
    
    public String getMeetingOfficialName() {
        return meetingOfficialName;
    }
    
    public void setMeetingOfficialName(String meetingOfficialName) {
        this.meetingOfficialName = meetingOfficialName;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
}
