package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "location_records")
public class LocationRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "driver_number")
    private Integer driverNumber;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @Column(name = "x")
    private Integer x;
    
    @Column(name = "y")
    private Integer y;
    
    @Column(name = "z")
    private Integer z;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public LocationRecord() {}
    
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
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    public Integer getX() {
        return x;
    }
    
    public void setX(Integer x) {
        this.x = x;
    }
    
    public Integer getY() {
        return y;
    }
    
    public void setY(Integer y) {
        this.y = y;
    }
    
    public Integer getZ() {
        return z;
    }
    
    public void setZ(Integer z) {
        this.z = z;
    }
}
