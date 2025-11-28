package com.example.f1.service;

import com.example.f1.dto.PositionDto;
import com.example.f1.dto.PositionResponse;
import com.example.f1.entity.PositionRecord;
import com.example.f1.repository.PositionRecordRepository;
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
public class OpenF1PositionService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1PositionService.class);
    
    @Autowired
    private PositionRecordRepository positionRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.position.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.position.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSavePositions() {
        fetchAndSavePositions(defaultMeetingKey, 
                            defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSavePositions(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/position")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching position data from: {}", url);
            
            PositionDto[] positionArray = restTemplate.getForObject(url, PositionDto[].class);
            
            if (positionArray != null && positionArray.length > 0) {
                List<PositionRecord> records = Arrays.stream(positionArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                positionRecordRepository.saveAll(records);
                logger.info("Saved {} position records", records.size());
            } else {
                logger.warn("No position data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching position data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private PositionRecord convertToEntity(PositionDto dto) {
        PositionRecord record = new PositionRecord();
        record.setDate(dto.getDate());
        record.setDriverNumber(dto.getDriverNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setPosition(dto.getPosition());
        record.setSessionKey(dto.getSessionKey());
        return record;
    }
    
    private PositionResponse convertToResponse(PositionRecord record) {
        PositionResponse response = new PositionResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDate(record.getDate());
        response.setDriverNumber(record.getDriverNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setPosition(record.getPosition());
        response.setSessionKey(record.getSessionKey());
        return response;
    }
    
    public Page<PositionResponse> getPositionsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<PositionRecord> records = positionRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<PositionResponse> getPositionsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<PositionRecord> records = positionRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<PositionResponse> getLatestPosition() {
        Optional<PositionRecord> record = positionRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
