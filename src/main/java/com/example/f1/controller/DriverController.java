package com.example.f1.controller;

import com.example.f1.dto.DriverResponse;
import com.example.f1.entity.DriverInfoRecord;
import com.example.f1.service.OpenF1DriverService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/f1/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    private final OpenF1DriverService driverService;
    private final Integer defaultDriverNumber;
    private final Long defaultMeetingKey;

    public DriverController(
            OpenF1DriverService driverService,
            @Value("${openf1.driver.default-driver-number:55}") Integer defaultDriverNumber,
            @Value("${openf1.driver.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.driverService = driverService;
        this.defaultDriverNumber = defaultDriverNumber;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey,
            @RequestParam(defaultValue = "10") int limit) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        List<DriverInfoRecord> records = driverService.getLatestBatch(resolvedDriver, resolvedMeeting, limit);

        List<DriverResponse> payload = records.stream()
                .map(DriverResponse::fromEntity)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedMeeting);
        response.put("driverNumber", resolvedDriver);
        response.put("count", payload.size());
        response.put("records", payload);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> latest(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        Optional<DriverInfoRecord> record = driverService.getLatest(resolvedDriver, resolvedMeeting);

        if (record.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(DriverResponse.fromEntity(record.get()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey,
            @RequestParam(required = false) Long sessionKey) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        List<DriverInfoRecord> saved = driverService.fetchAndSave(resolvedDriver, resolvedMeeting, sessionKey);

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedMeeting);
        response.put("driverNumber", resolvedDriver);
        response.put("saved", saved.size());
        response.put("records", saved.stream().map(DriverResponse::fromEntity).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}

