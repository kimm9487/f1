package com.example.f1.service;

import com.example.f1.dto.SessionResultDto;
import com.example.f1.dto.SessionResultResponse;
import com.example.f1.entity.SessionResultRecord;
import com.example.f1.repository.SessionResultRecordRepository;
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
public class OpenF1SessionResultService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1SessionResultService.class);
    
    @Autowired
    private SessionResultRecordRepository sessionResultRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.session-result.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.session-result.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveSessionResults() {
        fetchAndSaveSessionResults(defaultMeetingKey, 
                                  defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveSessionResults(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/session_result")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching session result data from: {}", url);
            
            SessionResultDto[] sessionResultArray = restTemplate.getForObject(url, SessionResultDto[].class);
            
            if (sessionResultArray != null && sessionResultArray.length > 0) {
                List<SessionResultRecord> records = Arrays.stream(sessionResultArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                sessionResultRecordRepository.saveAll(records);
                logger.info("Saved {} session result records", records.size());
            } else {
                logger.warn("No session result data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching session result data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private SessionResultRecord convertToEntity(SessionResultDto dto) {
        SessionResultRecord record = new SessionResultRecord();
        record.setDriverNumber(dto.getDriverNumber());
        record.setGridPosition(dto.getGridPosition());
        record.setMeetingKey(dto.getMeetingKey());
        record.setPoints(dto.getPoints());
        record.setPosition(dto.getPosition());
        record.setSessionKey(dto.getSessionKey());
        record.setTime(dto.getTime());
        return record;
    }
    
    private SessionResultResponse convertToResponse(SessionResultRecord record) {
        SessionResultResponse response = new SessionResultResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDriverNumber(record.getDriverNumber());
        response.setGridPosition(record.getGridPosition());
        response.setMeetingKey(record.getMeetingKey());
        response.setPoints(record.getPoints());
        response.setPosition(record.getPosition());
        response.setSessionKey(record.getSessionKey());
        response.setTime(record.getTime());
        return response;
    }
    
    public Page<SessionResultResponse> getSessionResultsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<SessionResultRecord> records = sessionResultRecordRepository.findByMeetingKeyOrderByPosition(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<SessionResultResponse> getSessionResultsBySessionKey(Integer sessionKey, Pageable pageable) {
        Page<SessionResultRecord> records = sessionResultRecordRepository.findBySessionKeyOrderByPosition(sessionKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<SessionResultResponse> getSessionResultsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<SessionResultRecord> records = sessionResultRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<SessionResultResponse> getLatestSessionResult() {
        Optional<SessionResultRecord> record = sessionResultRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
