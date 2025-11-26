package com.example.f1.controller;

import com.example.f1.dto.MeetingResponse;
import com.example.f1.service.OpenF1MeetingService;
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
@RequestMapping("/api/f1/meetings")
@CrossOrigin(origins = "*")
public class MeetingController {
    
    @Autowired
    private OpenF1MeetingService meetingService;
    
    @GetMapping
    public ResponseEntity<?> getMeetings(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MeetingResponse> meetings;
            
            if (year != null) {
                meetings = meetingService.getMeetingsByYear(year, pageable);
            } else if (meetingKey != null) {
                meetings = meetingService.getMeetingsByMeetingKey(meetingKey, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either year or meetingKey parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("meetings", meetings.getContent());
            response.put("currentPage", meetings.getNumber());
            response.put("totalItems", meetings.getTotalElements());
            response.put("totalPages", meetings.getTotalPages());
            response.put("pageSize", meetings.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch meeting data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMeeting() {
        try {
            Optional<MeetingResponse> latestMeeting = meetingService.getLatestMeeting();
            
            if (latestMeeting.isPresent()) {
                return ResponseEntity.ok(latestMeeting.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No meeting data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest meeting: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshMeetings(
            @RequestParam(required = false) Integer year) {
        
        try {
            if (year != null) {
                meetingService.fetchAndSaveMeetings(year);
            } else {
                meetingService.fetchAndSaveMeetings();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Meeting data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh meeting data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
