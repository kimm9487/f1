package com.example.f1.controller;

import com.example.f1.dto.IntervalResponse;
import com.example.f1.entity.IntervalRecord;
import com.example.f1.service.OpenF1IntervalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/f1/intervals")
@CrossOrigin(origins = "*")
public class IntervalController {

    private final OpenF1IntervalService intervalService;
    private final Integer defaultDriverNumber;
    private final Long defaultMeetingKey;

    public IntervalController(
            OpenF1IntervalService intervalService,
            @Value("${openf1.intervals.default-driver-number:55}") Integer defaultDriverNumber,
            @Value("${openf1.intervals.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.intervalService = intervalService;
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

        List<IntervalRecord> records = intervalService.getLatestBatch(resolvedDriver, resolvedMeeting, limit);

        List<IntervalResponse> payload = records.stream()
                .map(IntervalResponse::fromEntity)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedMeeting);
        response.put("driverNumber", resolvedDriver);
        response.put("count", payload.size());
        response.put("intervals", payload);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> latest(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        Optional<IntervalRecord> record = intervalService.getLatest(resolvedDriver, resolvedMeeting);

        if (record.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(IntervalResponse.fromEntity(record.get()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey,
            @RequestParam(required = false) Long sessionKey) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        List<IntervalRecord> records = intervalService.fetchAndSave(resolvedDriver, resolvedMeeting, sessionKey);

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedMeeting);
        response.put("driverNumber", resolvedDriver);
        response.put("saved", records.size());
        response.put("intervals", records.stream().map(IntervalResponse::fromEntity).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}

