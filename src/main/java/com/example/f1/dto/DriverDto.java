package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverDto {

    @JsonProperty("meeting_key")
    private Long meetingKey;

    @JsonProperty("session_key")
    private Long sessionKey;

    @JsonProperty("driver_number")
    private Integer driverNumber;

    @JsonProperty("broadcast_name")
    private String broadcastName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_colour")
    private String teamColour;

    @JsonProperty("headshot_url")
    private String headshotUrl;

    @JsonProperty("name_acronym")
    private String nameAcronym;

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
}

