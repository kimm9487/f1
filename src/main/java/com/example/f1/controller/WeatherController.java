package com.example.f1.controller;

import com.example.f1.dto.WeatherResponse;
import com.example.f1.entity.WeatherRecord;
import com.example.f1.service.OpenF1WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather", description = "F1 날씨 데이터 API")
public class WeatherController {

    private final OpenF1WeatherService weatherService;
    private final Long defaultMeetingKey;

    public WeatherController(OpenF1WeatherService weatherService,
                             @Value("${openf1.weather.default-meeting-key:1208}") Long defaultMeetingKey) {
        this.weatherService = weatherService;
        this.defaultMeetingKey = defaultMeetingKey;
    }

    @GetMapping
    @Operation(summary = "날씨 데이터 목록 조회", description = "특정 미팅의 날씨 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 데이터를 조회했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    public ResponseEntity<?> list(
            @Parameter(description = "미팅 키 (기본값: 1208)") @RequestParam(required = false) Long meetingKey,
            @Parameter(description = "조회할 레코드 수 (기본값: 10)") @RequestParam(defaultValue = "10") int limit) {
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
    @Operation(summary = "최신 날씨 데이터 조회", description = "특정 미팅의 가장 최신 날씨 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최신 데이터를 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "204", description = "데이터가 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    public ResponseEntity<?> latest(
            @Parameter(description = "미팅 키 (기본값: 1208)") @RequestParam(required = false) Long meetingKey) {
        Long resolvedKey = resolveMeetingKey(meetingKey);
        Optional<WeatherRecord> latest = weatherService.getLatestRecord(resolvedKey);

        if (latest.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(WeatherResponse.fromEntity(latest.get()));
    }

    @PostMapping("/refresh")
    @Operation(summary = "날씨 데이터 새로고침", description = "OpenF1 API에서 최신 날씨 데이터를 가져와 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "데이터를 성공적으로 새로고침했습니다."),
            @ApiResponse(responseCode = "500", description = "새로고침 중 오류가 발생했습니다.")
    })
    public ResponseEntity<?> refresh(
            @Parameter(description = "미팅 키 (기본값: 1208)") @RequestParam(required = false) Long meetingKey) {
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

