package com.example.f1.service;

import com.example.f1.dto.OvertakeDto;
import com.example.f1.dto.OvertakeResponse;
import com.example.f1.entity.OvertakeRecord;
import com.example.f1.repository.OvertakeRecordRepository;
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
public class OpenF1OvertakeService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1OvertakeService.class);
    
    @Autowired
    private OvertakeRecordRepository overtakeRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.overtakes.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.overtakes.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveOvertakes() {
        fetchAndSaveOvertakes(defaultMeetingKey, 
                            defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveOvertakes(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/overtakes")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching overtakes from: {}", url);
            
            OvertakeDto[] overtakeArray = restTemplate.getForObject(url, OvertakeDto[].class);
            
            if (overtakeArray != null && overtakeArray.length > 0) {
                List<OvertakeRecord> records = Arrays.stream(overtakeArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                overtakeRecordRepository.saveAll(records);
                logger.info("Saved {} overtake records", records.size());
            } else {
                logger.warn("No overtake data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching overtake data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private OvertakeRecord convertToEntity(OvertakeDto dto) {
        OvertakeRecord record = new OvertakeRecord();
        record.setDate(dto.getDate());
        record.setDriverNumber(dto.getDriverNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        return record;
    }
    
    private OvertakeResponse convertToResponse(OvertakeRecord record) {
        OvertakeResponse response = new OvertakeResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDate(record.getDate());
        response.setDriverNumber(record.getDriverNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setSessionKey(record.getSessionKey());
        return response;
    }
    
    public Page<OvertakeResponse> getOvertakesByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<OvertakeRecord> records = overtakeRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<OvertakeResponse> getOvertakesByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<OvertakeRecord> records = overtakeRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<OvertakeResponse> getLatestOvertake() {
        Optional<OvertakeRecord> record = overtakeRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
