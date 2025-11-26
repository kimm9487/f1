package com.example.f1.controller;

import com.example.f1.dto.OvertakeResponse;
import com.example.f1.service.OpenF1OvertakeService;
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
@RequestMapping("/api/f1/overtakes")
@CrossOrigin(origins = "*")
public class OvertakeController {
    
    @Autowired
    private OpenF1OvertakeService overtakeService;
    
    @GetMapping
    public ResponseEntity<?> getOvertakes(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OvertakeResponse> overtakes;
            
            if (meetingKey != null) {
                overtakes = overtakeService.getOvertakesByMeetingKey(meetingKey, pageable);
            } else if (driverNumber != null) {
                overtakes = overtakeService.getOvertakesByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("overtakes", overtakes.getContent());
            response.put("currentPage", overtakes.getNumber());
            response.put("totalItems", overtakes.getTotalElements());
            response.put("totalPages", overtakes.getTotalPages());
            response.put("pageSize", overtakes.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch overtake data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestOvertake() {
        try {
            Optional<OvertakeResponse> latestOvertake = overtakeService.getLatestOvertake();
            
            if (latestOvertake.isPresent()) {
                return ResponseEntity.ok(latestOvertake.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No overtake data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest overtake: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshOvertakes(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                overtakeService.fetchAndSaveOvertakes(meetingKey, sessionKey);
            } else {
                overtakeService.fetchAndSaveOvertakes();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Overtake data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh overtake data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
