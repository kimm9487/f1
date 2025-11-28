package com.example.f1.controller;

import com.example.f1.dto.LapResponse;
import com.example.f1.service.OpenF1LapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/f1/laps")
@CrossOrigin(origins = "*")
public class LapController {
    
    @Autowired
    private OpenF1LapService lapService;
    
    @GetMapping
    public ResponseEntity<?> getLaps(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<LapResponse> laps;
            
            if (meetingKey != null) {
                laps = lapService.getLapsByMeetingKey(meetingKey, pageable);
            } else if (driverNumber != null) {
                laps = lapService.getLapsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("laps", laps.getContent());
            response.put("currentPage", laps.getNumber());
            response.put("totalItems", laps.getTotalElements());
            response.put("totalPages", laps.getTotalPages());
            response.put("pageSize", laps.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch lap data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestLap() {
        try {
            Optional<LapResponse> latestLap = lapService.getLatestLap();
            
            if (latestLap.isPresent()) {
                return ResponseEntity.ok(latestLap.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No lap data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest lap: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshLaps(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null && driverNumber != null) {
                lapService.fetchAndSaveLaps(meetingKey, driverNumber, sessionKey);
            } else {
                lapService.fetchAndSaveLaps();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Lap data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh lap data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
