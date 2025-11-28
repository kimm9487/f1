package com.example.f1.controller;

import com.example.f1.dto.PitResponse;
import com.example.f1.service.OpenF1PitService;
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
@RequestMapping("/api/f1/pit")
@CrossOrigin(origins = "*")
public class PitController {
    
    @Autowired
    private OpenF1PitService pitService;
    
    @GetMapping
    public ResponseEntity<?> getPits(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PitResponse> pits;
            
            if (meetingKey != null) {
                pits = pitService.getPitsByMeetingKey(meetingKey, pageable);
            } else if (driverNumber != null) {
                pits = pitService.getPitsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("pits", pits.getContent());
            response.put("currentPage", pits.getNumber());
            response.put("totalItems", pits.getTotalElements());
            response.put("totalPages", pits.getTotalPages());
            response.put("pageSize", pits.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch pit data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestPit() {
        try {
            Optional<PitResponse> latestPit = pitService.getLatestPit();
            
            if (latestPit.isPresent()) {
                return ResponseEntity.ok(latestPit.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No pit data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest pit: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshPits(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                pitService.fetchAndSavePits(meetingKey, sessionKey);
            } else {
                pitService.fetchAndSavePits();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Pit data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh pit data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
