package com.example.f1.controller;

import com.example.f1.dto.RaceControlResponse;
import com.example.f1.service.OpenF1RaceControlService;
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
@RequestMapping("/api/f1/race-control")
@CrossOrigin(origins = "*")
public class RaceControlController {
    
    @Autowired
    private OpenF1RaceControlService raceControlService;
    
    @GetMapping
    public ResponseEntity<?> getRaceControls(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RaceControlResponse> raceControls;
            
            if (meetingKey != null) {
                raceControls = raceControlService.getRaceControlsByMeetingKey(meetingKey, pageable);
            } else if (category != null) {
                raceControls = raceControlService.getRaceControlsByCategory(category, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or category parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("raceControls", raceControls.getContent());
            response.put("currentPage", raceControls.getNumber());
            response.put("totalItems", raceControls.getTotalElements());
            response.put("totalPages", raceControls.getTotalPages());
            response.put("pageSize", raceControls.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch race control data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestRaceControl() {
        try {
            Optional<RaceControlResponse> latestRaceControl = raceControlService.getLatestRaceControl();
            
            if (latestRaceControl.isPresent()) {
                return ResponseEntity.ok(latestRaceControl.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No race control data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest race control: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshRaceControls(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                raceControlService.fetchAndSaveRaceControls(meetingKey, sessionKey);
            } else {
                raceControlService.fetchAndSaveRaceControls();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Race control data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh race control data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
