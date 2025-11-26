package com.example.f1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lap_records")
public class LapRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    
    @Column(name = "driver_number")
    private Integer driverNumber;
    
    @Column(name = "duration_sector_1")
    private Double durationSector1;
    
    @Column(name = "duration_sector_2")
    private Double durationSector2;
    
    @Column(name = "duration_sector_3")
    private Double durationSector3;
    
    @Column(name = "i1_speed")
    private Integer i1Speed;
    
    @Column(name = "i2_speed")
    private Integer i2Speed;
    
    @Column(name = "is_pit_out_lap")
    private Boolean isPitOutLap;
    
    @Column(name = "lap_duration")
    private Double lapDuration;
    
    @Column(name = "lap_number")
    private Integer lapNumber;
    
    @Column(name = "meeting_key")
    private Integer meetingKey;
    
    @Column(name = "session_key")
    private Integer sessionKey;
    
    @Column(name = "st_speed")
    private Integer stSpeed;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
    
    // Constructors
    public LapRecord() {}
    
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
    
    public Double getDurationSector1() {
        return durationSector1;
    }
    
    public void setDurationSector1(Double durationSector1) {
        this.durationSector1 = durationSector1;
    }
    
    public Double getDurationSector2() {
        return durationSector2;
    }
    
    public void setDurationSector2(Double durationSector2) {
        this.durationSector2 = durationSector2;
    }
    
    public Double getDurationSector3() {
        return durationSector3;
    }
    
    public void setDurationSector3(Double durationSector3) {
        this.durationSector3 = durationSector3;
    }
    
    public Integer getI1Speed() {
        return i1Speed;
    }
    
    public void setI1Speed(Integer i1Speed) {
        this.i1Speed = i1Speed;
    }
    
    public Integer getI2Speed() {
        return i2Speed;
    }
    
    public void setI2Speed(Integer i2Speed) {
        this.i2Speed = i2Speed;
    }
    
    public Boolean getIsPitOutLap() {
        return isPitOutLap;
    }
    
    public void setIsPitOutLap(Boolean isPitOutLap) {
        this.isPitOutLap = isPitOutLap;
    }
    
    public Double getLapDuration() {
        return lapDuration;
    }
    
    public void setLapDuration(Double lapDuration) {
        this.lapDuration = lapDuration;
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
    
    public Integer getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    public Integer getStSpeed() {
        return stSpeed;
    }
    
    public void setStSpeed(Integer stSpeed) {
        this.stSpeed = stSpeed;
    }
}
