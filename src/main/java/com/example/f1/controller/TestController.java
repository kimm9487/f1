package com.example.f1.controller;

import com.example.f1.service.OpenF1WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
@Tag(name = "Test", description = "API 테스트용 엔드포인트")
public class TestController {

    private final OpenF1WeatherService weatherService;

    public TestController(OpenF1WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/health")
    @Operation(summary = "헬스체크", description = "API 서버 상태를 확인합니다.")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "F1 Korea API is running");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/weather/manual-fetch")
    @Operation(summary = "수동 날씨 데이터 가져오기", description = "OpenF1에서 날씨 데이터를 수동으로 가져와 저장합니다.")
    public ResponseEntity<Map<String, Object>> manualWeatherFetch(
            @RequestParam(defaultValue = "1208") Long meetingKey) {
        try {
            var records = weatherService.fetchAndSaveWeather(meetingKey);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("meetingKey", meetingKey);
            response.put("recordsCount", records.size());
            response.put("message", "Weather data fetched successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
