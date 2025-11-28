package com.example.f1.service;

import com.example.f1.dto.PitDto;
import com.example.f1.dto.PitResponse;
import com.example.f1.entity.PitRecord;
import com.example.f1.repository.PitRecordRepository;
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
public class OpenF1PitService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1PitService.class);
    
    @Autowired
    private PitRecordRepository pitRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.pit.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.pit.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSavePits() {
        fetchAndSavePits(defaultMeetingKey, 
                        defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSavePits(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/pit")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching pit data from: {}", url);
            
            PitDto[] pitArray = restTemplate.getForObject(url, PitDto[].class);
            
            if (pitArray != null && pitArray.length > 0) {
                List<PitRecord> records = Arrays.stream(pitArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                pitRecordRepository.saveAll(records);
                logger.info("Saved {} pit records", records.size());
            } else {
                logger.warn("No pit data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching pit data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private PitRecord convertToEntity(PitDto dto) {
        PitRecord record = new PitRecord();
        record.setDate(dto.getDate());
        record.setDriverNumber(dto.getDriverNumber());
        record.setDuration(dto.getDuration());
        record.setLapNumber(dto.getLapNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setPitDuration(dto.getPitDuration());
        record.setSessionKey(dto.getSessionKey());
        return record;
    }
    
    private PitResponse convertToResponse(PitRecord record) {
        PitResponse response = new PitResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDate(record.getDate());
        response.setDriverNumber(record.getDriverNumber());
        response.setDuration(record.getDuration());
        response.setLapNumber(record.getLapNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setPitDuration(record.getPitDuration());
        response.setSessionKey(record.getSessionKey());
        return response;
    }
    
    public Page<PitResponse> getPitsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<PitRecord> records = pitRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<PitResponse> getPitsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<PitRecord> records = pitRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<PitResponse> getLatestPit() {
        Optional<PitRecord> record = pitRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
