package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_result_records")
public class SessionResultRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "driver_number")
    private Integer driverNumber;
    
    @Column(name = "grid_position")
    private Integer gridPosition;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "points")
    private Integer points;
    
    @Column(name = "position")
    private Integer position;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @Column(name = "time")
    private Double time;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public SessionResultRecord() {}
    
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
    
    public Integer getGridPosition() {
        return gridPosition;
    }
    
    public void setGridPosition(Integer gridPosition) {
        this.gridPosition = gridPosition;
    }
    
    public Integer getMeetingKey() {
        return meetingKey;
    }
    
    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
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
