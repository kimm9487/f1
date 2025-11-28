package com.example.f1.controller;

import com.example.f1.dto.PositionResponse;
import com.example.f1.service.OpenF1PositionService;
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
@RequestMapping("/api/f1/position")
@CrossOrigin(origins = "*")
public class PositionController {
    
    @Autowired
    private OpenF1PositionService positionService;
    
    @GetMapping
    public ResponseEntity<?> getPositions(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PositionResponse> positions;
            
            if (meetingKey != null) {
                positions = positionService.getPositionsByMeetingKey(meetingKey, pageable);
            } else if (driverNumber != null) {
                positions = positionService.getPositionsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("positions", positions.getContent());
            response.put("currentPage", positions.getNumber());
            response.put("totalItems", positions.getTotalElements());
            response.put("totalPages", positions.getTotalPages());
            response.put("pageSize", positions.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch position data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestPosition() {
        try {
            Optional<PositionResponse> latestPosition = positionService.getLatestPosition();
            
            if (latestPosition.isPresent()) {
                return ResponseEntity.ok(latestPosition.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No position data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest position: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshPositions(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                positionService.fetchAndSavePositions(meetingKey, sessionKey);
            } else {
                positionService.fetchAndSavePositions();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Position data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh position data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
