package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_records")
public class SessionRecord {
    
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
    
    @Column(name = "date_end")
    private String dateEnd;
    
    @Column(name = "date_start")
    private String dateStart;
    
    @Column(name = "gmt_offset")
    private String gmtOffset;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @Column(name = "session_name")
    private String sessionName;
    
    @Column(name = "session_type")
    private String sessionType;
    
    @Column(name = "year")
    private Integer year;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public SessionRecord() {}
    
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
