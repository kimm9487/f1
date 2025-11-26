package com.example.f1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver_info_records")
public class DriverInfoRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_info_record_id")
    private Long id;

    @Column(name = "meeting_key")
    private Long meetingKey;

    @Column(name = "session_key")
    private Long sessionKey;

    @Column(name = "driver_number")
    private Integer driverNumber;

    @Column(name = "broadcast_name")
    private String broadcastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "team_colour")
    private String teamColour;

    @Column(name = "headshot_url")
    private String headshotUrl;

    @Column(name = "name_acronym")
    private String nameAcronym;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @PrePersist
    public void prePersist() {
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Long meetingKey) {
        this.meetingKey = meetingKey;
    }

    public Long getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Long sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public void setTeamColour(String teamColour) {
        this.teamColour = teamColour;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }

    public void setHeadshotUrl(String headshotUrl) {
        this.headshotUrl = headshotUrl;
    }

    public String getNameAcronym() {
        return nameAcronym;
    }

    public void setNameAcronym(String nameAcronym) {
        this.nameAcronym = nameAcronym;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}

