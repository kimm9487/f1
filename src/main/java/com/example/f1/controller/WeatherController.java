package com.example.f1.controller;

import com.example.f1.dto.WeatherResponse;
import com.example.f1.entity.WeatherRecord;
import com.example.f1.service.OpenF1WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/f1/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final OpenF1WeatherService weatherService;
    private final Long defaultMeetingKey;

    public WeatherController(OpenF1WeatherService weatherService,
                             @Value("${openf1.weather.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.weatherService = weatherService;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) Long meetingKey,
            @RequestParam(defaultValue = "10") int limit) {
        Long resolvedKey = resolveMeetingKey(meetingKey);
        List<WeatherRecord> records = weatherService.getLatestRecords(resolvedKey, limit);

        List<WeatherResponse> payload = records.stream()
                .map(WeatherResponse::fromEntity)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedKey);
        response.put("count", payload.size());
        response.put("weather", payload);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> latest(@RequestParam(required = false) Long meetingKey) {
        Long resolvedKey = resolveMeetingKey(meetingKey);
        Optional<WeatherRecord> latest = weatherService.getLatestRecord(resolvedKey);

        if (latest.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(WeatherResponse.fromEntity(latest.get()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam(required = false) Long meetingKey) {
        Long resolvedKey = resolveMeetingKey(meetingKey);
        List<WeatherRecord> records = weatherService.fetchAndSaveWeather(resolvedKey);

        Map<String, Object> response = new HashMap<>();
        response.put("meetingKey", resolvedKey);
        response.put("saved", records.size());
        response.put("records", records.stream()
                .map(WeatherResponse::fromEntity)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

    private Long resolveMeetingKey(Long meetingKey) {
        return meetingKey != null ? meetingKey : defaultMeetingKey;
    }
}

