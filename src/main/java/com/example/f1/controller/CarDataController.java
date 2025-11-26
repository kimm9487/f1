package com.example.f1.controller;

import com.example.f1.dto.CarDataResponse;
import com.example.f1.entity.CarDataRecord;
import com.example.f1.service.OpenF1CarDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/f1/car-data")
@CrossOrigin(origins = "*")
public class CarDataController {

    private final OpenF1CarDataService carDataService;
    private final Integer defaultDriverNumber;
    private final Long defaultMeetingKey;

    public CarDataController(
            OpenF1CarDataService carDataService,
            @Value("${openf1.cardata.default-driver-number:55}") Integer defaultDriverNumber,
            @Value("${openf1.cardata.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.carDataService = carDataService;
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

        List<CarDataRecord> records = carDataService.getLatestBatch(resolvedDriver, resolvedMeeting, limit);

        List<CarDataResponse> payload = records.stream()
                .map(CarDataResponse::fromEntity)
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

        Optional<CarDataRecord> latest = carDataService.getLatest(resolvedDriver, resolvedMeeting);

        if (latest.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CarDataResponse.fromEntity(latest.get()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Long meetingKey,
            @RequestParam(required = false) Long sessionKey) {
        Integer resolvedDriver = driverNumber != null ? driverNumber : defaultDriverNumber;
        Long resolvedMeeting = meetingKey != null ? meetingKey : defaultMeetingKey;

        List<CarDataRecord> records = carDataService.fetchAndSave(resolvedDriver, resolvedMeeting, sessionKey);

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedMeeting);
        response.put("driverNumber", resolvedDriver);
        response.put("saved", records.size());
        response.put("records", records.stream().map(CarDataResponse::fromEntity).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}

