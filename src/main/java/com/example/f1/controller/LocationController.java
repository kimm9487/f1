package com.example.f1.controller;

import com.example.f1.dto.LocationResponse;
import com.example.f1.service.OpenF1LocationService;
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
@RequestMapping("/api/f1/location")
@CrossOrigin(origins = "*")
public class LocationController {
    
    @Autowired
    private OpenF1LocationService locationService;
    
    @GetMapping
    public ResponseEntity<?> getLocations(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<LocationResponse> locations;
            
            if (meetingKey != null) {
                locations = locationService.getLocationsByMeetingKey(meetingKey, pageable);
            } else if (driverNumber != null) {
                locations = locationService.getLocationsByDriverNumber(driverNumber, pageable);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Either meetingKey or driverNumber parameter is required");
                return ResponseEntity.badRequest().body(error);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("locations", locations.getContent());
            response.put("currentPage", locations.getNumber());
            response.put("totalItems", locations.getTotalElements());
            response.put("totalPages", locations.getTotalPages());
            response.put("pageSize", locations.getSize());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch location data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestLocation() {
        try {
            Optional<LocationResponse> latestLocation = locationService.getLatestLocation();
            
            if (latestLocation.isPresent()) {
                return ResponseEntity.ok(latestLocation.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No location data available");
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch latest location: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshLocations(
            @RequestParam(required = false) Integer meetingKey,
            @RequestParam(required = false) Integer driverNumber,
            @RequestParam(required = false) Integer sessionKey) {
        
        try {
            if (meetingKey != null && driverNumber != null) {
                locationService.fetchAndSaveLocations(meetingKey, driverNumber, sessionKey);
            } else {
                locationService.fetchAndSaveLocations();
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Location data refresh completed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to refresh location data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
