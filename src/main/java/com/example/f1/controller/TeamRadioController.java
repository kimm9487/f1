package com.example.f1.controller;

import com.example.f1.dto.TeamRadioResponse;
import com.example.f1.service.OpenF1TeamRadioService;
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
@RequestMapping("/api/f1/team-radio")
@CrossOrigin(origins = "*")
public class TeamRadioController {
    
    @Autowired
    private OpenF1TeamRadioService teamRadioService;
    
    @GetMapping
    public ResponseEntity<?> getTeamRadios(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TeamRadioResponse> teamRadios;
            
            if (meetingKey != null) {
                teamRadios = teamRadioService.getTeamRadiosByMeetingKey(meetingKey, pageable);
            } else if (sessionKey != null) {
                teamRadios = teamRadioService.getTeamRadiosBySessionKey(sessionKey, pageable);
            } else if (driverNumber != null) {
                teamRadios = teamRadioService.getTeamRadiosByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey, sessionKey, or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("teamRadios", teamRadios.getContent());
            response.put("currentPage", teamRadios.getNumber());
            response.put("totalItems", teamRadios.getTotalElements());
            response.put("totalPages", teamRadios.getTotalPages());
            response.put("pageSize", teamRadios.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch team radio data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestTeamRadio() {
        try {
            Optional<TeamRadioResponse> latestTeamRadio = teamRadioService.getLatestTeamRadio();
            
            if (latestTeamRadio.isPresent()) {
                return ResponseEntity.ok(latestTeamRadio.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No team radio data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest team radio: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshTeamRadios(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                teamRadioService.fetchAndSaveTeamRadios(meetingKey, sessionKey);
            } else {
                teamRadioService.fetchAndSaveTeamRadios();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Team radio data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh team radio data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
