package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stint_records")
public class StintRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "compound")
    private String compound;
    
    @Column(name = "driver_number")
    private Integer driverNumber;
    
    @Column(name = "lap_end")
    private Integer lapEnd;
    
    @Column(name = "lap_start")
    private Integer lapStart;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @Column(name = "stint_number")
    private Integer stintNumber;
    
    @Column(name = "tyre_age_at_start")
    private Integer tyreAgeAtStart;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public StintRecord() {}
    
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
