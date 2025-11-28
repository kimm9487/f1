package com.example.f1.dto;

import com.example.f1.entity.DriverInfoRecord;

import java.time.LocalDateTime;

public class DriverResponse {

    private Long meetingKey;
    private Long sessionKey;
    private Integer driverNumber;
    private String broadcastName;
    private String firstName;
    private String lastName;
    private String fullName;
    private String countryCode;
    private String teamName;
    private String teamColour;
    private String headshotUrl;
    private String nameAcronym;
    private LocalDateTime recordedAt;

    public static DriverResponse fromEntity(DriverInfoRecord record) {
        if (record == null) {
            return null;
        }

        DriverResponse response = new DriverResponse();
        response.meetingKey = record.getMeetingKey();
        response.sessionKey = record.getSessionKey();
        response.driverNumber = record.getDriverNumber();
        response.broadcastName = record.getBroadcastName();
        response.firstName = record.getFirstName();
        response.lastName = record.getLastName();
        response.fullName = record.getFullName();
        response.countryCode = record.getCountryCode();
        response.teamName = record.getTeamName();
        response.teamColour = record.getTeamColour();
        response.headshotUrl = record.getHeadshotUrl();
        response.nameAcronym = record.getNameAcronym();
        response.recordedAt = record.getRecordedAt();
        return response;
    }

    public Long getMeetingKey() {
        return meetingKey;
    }

    public Long getSessionKey() {
        return sessionKey;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }

    public String getNameAcronym() {
        return nameAcronym;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
}

