package com.example.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "F1 차량 텔레메트리 데이터")
public class CarDataDto {

    @Schema(description = "미팅 키", example = "1208")
    @JsonProperty("meeting_key")
    private Long meetingKey;

    @Schema(description = "세션 키", example = "9158")
    @JsonProperty("session_key")
    private Long sessionKey;

    @Schema(description = "드라이버 번호", example = "55")
    @JsonProperty("driver_number")
    private Integer driverNumber;

    @Schema(description = "데이터 수집 시간", example = "2024-11-27T10:30:00Z")
    private OffsetDateTime date;

    @Schema(description = "속도 (km/h)", example = "320")
    private Integer speed;

    @Schema(description = "스로틀 위치 (%)", example = "100")
    private Integer throttle;

    @Schema(description = "브레이크 압력", example = "0")
    private Integer brake;

    @Schema(description = "엔진 RPM", example = "12000")
    private Integer rpm;

    @Schema(description = "DRS 상태 (0=닫힘, 1=열림)", example = "1")
    private Integer drs;

    @JsonProperty("n_gear")
    private Integer gear;

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

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public void setThrottle(Integer throttle) {
        this.throttle = throttle;
    }

    public Integer getBrake() {
        return brake;
    }

    public void setBrake(Integer brake) {
        this.brake = brake;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Integer getDrs() {
        return drs;
    }

    public void setDrs(Integer drs) {
        this.drs = drs;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }
}

