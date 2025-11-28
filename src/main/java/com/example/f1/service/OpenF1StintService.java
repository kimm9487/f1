package com.example.f1.service;

import com.example.f1.dto.StintDto;
import com.example.f1.dto.StintResponse;
import com.example.f1.entity.StintRecord;
import com.example.f1.repository.StintRecordRepository;
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
public class OpenF1StintService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1StintService.class);
    
    @Autowired
    private StintRecordRepository stintRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.stints.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.stints.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveStints() {
        fetchAndSaveStints(defaultMeetingKey, 
                          defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveStints(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/stints")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching stint data from: {}", url);
            
            StintDto[] stintArray = restTemplate.getForObject(url, StintDto[].class);
            
            if (stintArray != null && stintArray.length > 0) {
                List<StintRecord> records = Arrays.stream(stintArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                stintRecordRepository.saveAll(records);
                logger.info("Saved {} stint records", records.size());
            } else {
                logger.warn("No stint data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching stint data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private StintRecord convertToEntity(StintDto dto) {
        StintRecord record = new StintRecord();
        record.setCompound(dto.getCompound());
        record.setDriverNumber(dto.getDriverNumber());
        record.setLapEnd(dto.getLapEnd());
        record.setLapStart(dto.getLapStart());
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setStintNumber(dto.getStintNumber());
        record.setTyreAgeAtStart(dto.getTyreAgeAtStart());
        return record;
    }
    
    private StintResponse convertToResponse(StintRecord record) {
        StintResponse response = new StintResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setCompound(record.getCompound());
        response.setDriverNumber(record.getDriverNumber());
        response.setLapEnd(record.getLapEnd());
        response.setLapStart(record.getLapStart());
        response.setMeetingKey(record.getMeetingKey());
        response.setSessionKey(record.getSessionKey());
        response.setStintNumber(record.getStintNumber());
        response.setTyreAgeAtStart(record.getTyreAgeAtStart());
        return response;
    }
    
    public Page<StintResponse> getStintsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<StintRecord> records = stintRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<StintResponse> getStintsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<StintRecord> records = stintRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<StintResponse> getStintsBySessionKey(Integer sessionKey, Pageable pageable) {
        Page<StintRecord> records = stintRecordRepository.findBySessionKeyOrderByDateCreatedDesc(sessionKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<StintResponse> getLatestStint() {
        Optional<StintRecord> record = stintRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
