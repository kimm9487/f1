package com.example.f1.service;

import com.example.f1.dto.StartingGridDto;
import com.example.f1.dto.StartingGridResponse;
import com.example.f1.entity.StartingGridRecord;
import com.example.f1.repository.StartingGridRecordRepository;
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
public class OpenF1StartingGridService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1StartingGridService.class);
    
    @Autowired
    private StartingGridRecordRepository startingGridRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.starting-grid.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.starting-grid.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveStartingGrids() {
        fetchAndSaveStartingGrids(defaultMeetingKey, 
                                 defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveStartingGrids(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/starting_grid")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching starting grid data from: {}", url);
            
            StartingGridDto[] startingGridArray = restTemplate.getForObject(url, StartingGridDto[].class);
            
            if (startingGridArray != null && startingGridArray.length > 0) {
                List<StartingGridRecord> records = Arrays.stream(startingGridArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                startingGridRecordRepository.saveAll(records);
                logger.info("Saved {} starting grid records", records.size());
            } else {
                logger.warn("No starting grid data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching starting grid data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private StartingGridRecord convertToEntity(StartingGridDto dto) {
        StartingGridRecord record = new StartingGridRecord();
        record.setDriverNumber(dto.getDriverNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setPosition(dto.getPosition());
        record.setSessionKey(dto.getSessionKey());
        record.setTime(dto.getTime());
        return record;
    }
    
    private StartingGridResponse convertToResponse(StartingGridRecord record) {
        StartingGridResponse response = new StartingGridResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDriverNumber(record.getDriverNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setPosition(record.getPosition());
        response.setSessionKey(record.getSessionKey());
        response.setTime(record.getTime());
        return response;
    }
    
    public Page<StartingGridResponse> getStartingGridsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<StartingGridRecord> records = startingGridRecordRepository.findByMeetingKeyOrderByPosition(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<StartingGridResponse> getStartingGridsBySessionKey(Integer sessionKey, Pageable pageable) {
        Page<StartingGridRecord> records = startingGridRecordRepository.findBySessionKeyOrderByPosition(sessionKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<StartingGridResponse> getStartingGridsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<StartingGridRecord> records = startingGridRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<StartingGridResponse> getLatestStartingGrid() {
        Optional<StartingGridRecord> record = startingGridRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
