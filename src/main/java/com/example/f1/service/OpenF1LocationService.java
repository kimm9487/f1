package com.example.f1.service;

import com.example.f1.dto.LocationDto;
import com.example.f1.dto.LocationResponse;
import com.example.f1.entity.LocationRecord;
import com.example.f1.repository.LocationRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenF1LocationService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1LocationService.class);
    
    @Autowired
    private LocationRecordRepository locationRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.location.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.location.default-driver-number}")
    private Integer defaultDriverNumber;
    
    @Value("${openf1.location.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveLocations() {
        fetchAndSaveLocations(defaultMeetingKey, defaultDriverNumber, 
                            defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveLocations(Integer meetingKey, Integer driverNumber, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/location")
                    .queryParam("meeting_key", meetingKey)
                    .queryParam("driver_number", driverNumber);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching locations from: {}", url);
            
            LocationDto[] locationArray = restTemplate.getForObject(url, LocationDto[].class);
            
            if (locationArray != null && locationArray.length > 0) {
                List<LocationRecord> records = Arrays.stream(locationArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                locationRecordRepository.saveAll(records);
                logger.info("Saved {} location records", records.size());
            } else {
                logger.warn("No location data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching location data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private LocationRecord convertToEntity(LocationDto dto) {
        LocationRecord record = new LocationRecord();
        record.setDriverNumber(dto.getDriverNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setX(dto.getX());
        record.setY(dto.getY());
        record.setZ(dto.getZ());
        return record;
    }
    
    private LocationResponse convertToResponse(LocationRecord record) {
        LocationResponse response = new LocationResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDriverNumber(record.getDriverNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setSessionKey(record.getSessionKey());
        response.setX(record.getX());
        response.setY(record.getY());
        response.setZ(record.getZ());
        return response;
    }
    
    public Page<LocationResponse> getLocationsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<LocationRecord> records = locationRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<LocationResponse> getLocationsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<LocationRecord> records = locationRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<LocationResponse> getLatestLocation() {
        Optional<LocationRecord> record = locationRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
