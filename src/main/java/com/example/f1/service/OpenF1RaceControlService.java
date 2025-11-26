package com.example.f1.service;

import com.example.f1.dto.RaceControlDto;
import com.example.f1.dto.RaceControlResponse;
import com.example.f1.entity.RaceControlRecord;
import com.example.f1.repository.RaceControlRecordRepository;
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
public class OpenF1RaceControlService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1RaceControlService.class);
    
    @Autowired
    private RaceControlRecordRepository raceControlRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.race-control.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.race-control.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveRaceControls() {
        fetchAndSaveRaceControls(defaultMeetingKey, 
                                defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveRaceControls(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/race_control")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching race control data from: {}", url);
            
            RaceControlDto[] raceControlArray = restTemplate.getForObject(url, RaceControlDto[].class);
            
            if (raceControlArray != null && raceControlArray.length > 0) {
                List<RaceControlRecord> records = Arrays.stream(raceControlArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                raceControlRecordRepository.saveAll(records);
                logger.info("Saved {} race control records", records.size());
            } else {
                logger.warn("No race control data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching race control data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private RaceControlRecord convertToEntity(RaceControlDto dto) {
        RaceControlRecord record = new RaceControlRecord();
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setDriverNumber(dto.getDriverNumber());
        record.setFlag(dto.getFlag());
        record.setLapNumber(dto.getLapNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setMessage(dto.getMessage());
        record.setScope(dto.getScope());
        record.setSector(dto.getSector());
        record.setSessionKey(dto.getSessionKey());
        return record;
    }
    
    private RaceControlResponse convertToResponse(RaceControlRecord record) {
        RaceControlResponse response = new RaceControlResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setCategory(record.getCategory());
        response.setDate(record.getDate());
        response.setDriverNumber(record.getDriverNumber());
        response.setFlag(record.getFlag());
        response.setLapNumber(record.getLapNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setMessage(record.getMessage());
        response.setScope(record.getScope());
        response.setSector(record.getSector());
        response.setSessionKey(record.getSessionKey());
        return response;
    }
    
    public Page<RaceControlResponse> getRaceControlsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<RaceControlRecord> records = raceControlRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<RaceControlResponse> getRaceControlsByCategory(String category, Pageable pageable) {
        Page<RaceControlRecord> records = raceControlRecordRepository.findByCategoryOrderByDateCreatedDesc(category, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<RaceControlResponse> getLatestRaceControl() {
        Optional<RaceControlRecord> record = raceControlRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
