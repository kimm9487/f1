package com.example.f1.controller;

import com.example.f1.dto.SessionResultResponse;
import com.example.f1.service.OpenF1SessionResultService;
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
@RequestMapping("/api/f1/session-result")
@CrossOrigin(origins = "*")
public class SessionResultController {
    
    @Autowired
    private OpenF1SessionResultService sessionResultService;
    
    @GetMapping
    public ResponseEntity<?> getSessionResults(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SessionResultResponse> sessionResults;
            
            if (meetingKey != null) {
                sessionResults = sessionResultService.getSessionResultsByMeetingKey(meetingKey, pageable);
            } else if (sessionKey != null) {
                sessionResults = sessionResultService.getSessionResultsBySessionKey(sessionKey, pageable);
            } else if (driverNumber != null) {
                sessionResults = sessionResultService.getSessionResultsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey, sessionKey, or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("sessionResults", sessionResults.getContent());
            response.put("currentPage", sessionResults.getNumber());
            response.put("totalItems", sessionResults.getTotalElements());
            response.put("totalPages", sessionResults.getTotalPages());
            response.put("pageSize", sessionResults.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch session result data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestSessionResult() {
        try {
            Optional<SessionResultResponse> latestSessionResult = sessionResultService.getLatestSessionResult();
            
            if (latestSessionResult.isPresent()) {
                return ResponseEntity.ok(latestSessionResult.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No session result data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest session result: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshSessionResults(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null) {
                sessionResultService.fetchAndSaveSessionResults(meetingKey, sessionKey);
            } else {
                sessionResultService.fetchAndSaveSessionResults();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Session result data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh session result data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
