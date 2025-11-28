package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "F1 날씨 데이터")
public class WeatherDto {

    @Schema(description = "미팅 키", example = "1208")
    @JsonProperty("meeting_key")
    private Long meetingKey;

    @Schema(description = "세션 키", example = "9158")
    @JsonProperty("session_key")
    private Long sessionKey;

    @Schema(description = "데이터 수집 시간", example = "2024-11-27T10:30:00Z")
    private OffsetDateTime date;

    @Schema(description = "대기 온도 (°C)", example = "25.5")
    @JsonProperty("air_temperature")
    private Double airTemperature;

    @Schema(description = "트랙 온도 (°C)", example = "35.2")
    @JsonProperty("track_temperature")
    private Double trackTemperature;

    @Schema(description = "풍속 (m/s)", example = "5.2")
    @JsonProperty("wind_speed")
    private Double windSpeed;

    @Schema(description = "풍향 (도)", example = "180")
    @JsonProperty("wind_direction")
    private Integer windDirection;

    @Schema(description = "습도 (%)", example = "65")
    private Integer humidity;

    @Schema(description = "기압 (hPa)", example = "1013.2")
    private Double pressure;

    @Schema(description = "강수량 (mm)", example = "0.0")
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

