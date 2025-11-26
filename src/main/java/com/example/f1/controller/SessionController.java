package com.example.f1.controller;

import com.example.f1.dto.SessionResponse;
import com.example.f1.service.OpenF1SessionService;
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
@RequestMapping("/api/f1/sessions")
@CrossOrigin(origins = "*")
public class SessionController {
    
    @Autowired
    private OpenF1SessionService sessionService;
    
    @GetMapping
    public ResponseEntity<?> getSessions(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String sessionType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SessionResponse> sessions;
            
            if (meetingKey != null) {
                sessions = sessionService.getSessionsByMeetingKey(meetingKey, pageable);
            } else if (year != null) {
                sessions = sessionService.getSessionsByYear(year, pageable);
            } else if (sessionType != null) {
                sessions = sessionService.getSessionsByType(sessionType, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey, year, or sessionType parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("sessions", sessions.getContent());
            response.put("currentPage", sessions.getNumber());
            response.put("totalItems", sessions.getTotalElements());
            response.put("totalPages", sessions.getTotalPages());
            response.put("pageSize", sessions.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch session data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestSession() {
        try {
            Optional<SessionResponse> latestSession = sessionService.getLatestSession();
            
            if (latestSession.isPresent()) {
                return ResponseEntity.ok(latestSession.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No session data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest session: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshSessions(
            @RequestParam(required = false) Integer year) {
        
        try {
            if (year != null) {
                sessionService.fetchAndSaveSessions(year);
            } else {
                sessionService.fetchAndSaveSessions();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Session data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh session data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
