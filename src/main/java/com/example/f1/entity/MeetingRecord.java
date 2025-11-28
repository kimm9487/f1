package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_records")
public class MeetingRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "circuit_key")
    private Integer circuitKey;
    
    @Column(name = "circuit_short_name")
    private String circuitShortName;
    
    @Column(name = "country_code")
    private String countryCode;
    
    @Column(name = "country_key")
    private Integer countryKey;
    
    @Column(name = "country_name")
    private String countryName;
    
    @Column(name = "date_start")
    private String dateStart;
    
    @Column(name = "gmt_offset")
    private String gmtOffset;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "meeting_name")
    private String meetingName;
    
    @Column(name = "meeting_official_name")
    private String meetingOfficialName;
    
    @Column(name = "year")
    private Integer year;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public MeetingRecord() {}
    
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
