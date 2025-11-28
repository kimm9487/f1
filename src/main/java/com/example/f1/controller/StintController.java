package com.example.f1.controller;

import com.example.f1.dto.StintResponse;
import com.example.f1.service.OpenF1StintService;
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
@RequestMapping("/api/f1/stints")
@CrossOrigin(origins = "*")
public class StintController {
    
    @Autowired
    private OpenF1StintService stintService;
    
    @GetMapping
    public ResponseEntity<?> getStints(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<StintResponse> stints;
            
            if (meetingKey != null) {
                stints = stintService.getStintsByMeetingKey(meetingKey, pageable);
            } else if (sessionKey != null) {
                stints = stintService.getStintsBySessionKey(sessionKey, pageable);
            } else if (driverNumber != null) {
                stints = stintService.getStintsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey, sessionKey, or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("stints", stints.getContent());
            response.put("currentPage", stints.getNumber());
            response.put("totalItems", stints.getTotalElements());
            response.put("totalPages", stints.getTotalPages());
            response.put("pageSize", stints.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch stint data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestStint() {
        try {
            Optional<StintResponse> latestStint = stintService.getLatestStint();
            
            if (latestStint.isPresent()) {
                return ResponseEntity.ok(latestStint.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No stint data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest stint: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshStints(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                stintService.fetchAndSaveStints(meetingKey, sessionKey);
            } else {
                stintService.fetchAndSaveStints();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Stint data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh stint data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
