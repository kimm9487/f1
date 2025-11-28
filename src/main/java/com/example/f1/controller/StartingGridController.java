package com.example.f1.controller;

import com.example.f1.dto.StartingGridResponse;
import com.example.f1.service.OpenF1StartingGridService;
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
@RequestMapping("/api/f1/starting-grid")
@CrossOrigin(origins = "*")
public class StartingGridController {
    
    @Autowired
    private OpenF1StartingGridService startingGridService;
    
    @GetMapping
    public ResponseEntity<?> getStartingGrids(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<StartingGridResponse> startingGrids;
            
            if (meetingKey != null) {
                startingGrids = startingGridService.getStartingGridsByMeetingKey(meetingKey, pageable);
            } else if (sessionKey != null) {
                startingGrids = startingGridService.getStartingGridsBySessionKey(sessionKey, pageable);
            } else if (driverNumber != null) {
                startingGrids = startingGridService.getStartingGridsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey, sessionKey, or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("startingGrids", startingGrids.getContent());
            response.put("currentPage", startingGrids.getNumber());
            response.put("totalItems", startingGrids.getTotalElements());
            response.put("totalPages", startingGrids.getTotalPages());
            response.put("pageSize", startingGrids.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch starting grid data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestStartingGrid() {
        try {
            Optional<StartingGridResponse> latestStartingGrid = startingGridService.getLatestStartingGrid();
            
            if (latestStartingGrid.isPresent()) {
                return ResponseEntity.ok(latestStartingGrid.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No starting grid data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest starting grid: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshStartingGrids(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                startingGridService.fetchAndSaveStartingGrids(meetingKey, sessionKey);
            } else {
                startingGridService.fetchAndSaveStartingGrids();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Starting grid data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh starting grid data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
