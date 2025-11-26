package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class WeatherDto {

    @JsonProperty("meeting_key")
    private Long meetingKey;

    @JsonProperty("session_key")
    private Long sessionKey;

    private OffsetDateTime date;

    @JsonProperty("air_temperature")
    private Double airTemperature;

    @JsonProperty("track_temperature")
    private Double trackTemperature;

    @JsonProperty("wind_speed")
    private Double windSpeed;

    @JsonProperty("wind_direction")
    private Integer windDirection;

    private Integer humidity;

    private Double pressure;

    private Double rainfall;

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

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getTrackTemperature() {
        return trackTemperature;
    }

    public void setTrackTemperature(Double trackTemperature) {
        this.trackTemperature = trackTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getRainfall() {
        return rainfall;
    }

    public void setRainfall(Double rainfall) {
        this.rainfall = rainfall;
    }
}

