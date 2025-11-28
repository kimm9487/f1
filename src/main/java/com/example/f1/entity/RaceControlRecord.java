package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "race_control_records")
public class RaceControlRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "date")
    private String date;
    
    @Column(name = "driver_number")
    private Integer driverNumber;
    
    @Column(name = "flag")
    private String flag;
    
    @Column(name = "lap_number")
    private Integer lapNumber;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "scope")
    private String scope;
    
    @Column(name = "sector")
    private Integer sector;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public RaceControlRecord() {}
    
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
