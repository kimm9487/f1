package com.example.f1.service;

import com.example.f1.dto.SessionDto;
import com.example.f1.dto.SessionResponse;
import com.example.f1.entity.SessionRecord;
import com.example.f1.repository.SessionRecordRepository;
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
public class OpenF1SessionService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1SessionService.class);
    
    @Autowired
    private SessionRecordRepository sessionRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.sessions.default-year}")
    private Integer defaultYear;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveSessions() {
        fetchAndSaveSessions(defaultYear);
    }
    
    public void fetchAndSaveSessions(Integer year) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/sessions");
            
            if (year != null) {
                uriBuilder.queryParam("year", year);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching sessions from: {}", url);
            
            SessionDto[] sessionArray = restTemplate.getForObject(url, SessionDto[].class);
            
            if (sessionArray != null && sessionArray.length > 0) {
                List<SessionRecord> records = Arrays.stream(sessionArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                sessionRecordRepository.saveAll(records);
                logger.info("Saved {} session records", records.size());
            } else {
                logger.warn("No session data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching session data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private SessionRecord convertToEntity(SessionDto dto) {
        SessionRecord record = new SessionRecord();
        record.setCircuitKey(dto.getCircuitKey());
        record.setCircuitShortName(dto.getCircuitShortName());
        record.setCountryCode(dto.getCountryCode());
        record.setCountryKey(dto.getCountryKey());
        record.setCountryName(dto.getCountryName());
        record.setDateEnd(dto.getDateEnd());
        record.setDateStart(dto.getDateStart());
        record.setGmtOffset(dto.getGmtOffset());
        record.setLocation(dto.getLocation());
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setSessionName(dto.getSessionName());
        record.setSessionType(dto.getSessionType());
        record.setYear(dto.getYear());
        return record;
    }
    
    private SessionResponse convertToResponse(SessionRecord record) {
        SessionResponse response = new SessionResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setCircuitKey(record.getCircuitKey());
        response.setCircuitShortName(record.getCircuitShortName());
        response.setCountryCode(record.getCountryCode());
        response.setCountryKey(record.getCountryKey());
        response.setCountryName(record.getCountryName());
        response.setDateEnd(record.getDateEnd());
        response.setDateStart(record.getDateStart());
        response.setGmtOffset(record.getGmtOffset());
        response.setLocation(record.getLocation());
        response.setMeetingKey(record.getMeetingKey());
        response.setSessionKey(record.getSessionKey());
        response.setSessionName(record.getSessionName());
        response.setSessionType(record.getSessionType());
        response.setYear(record.getYear());
        return response;
    }
    
    public Page<SessionResponse> getSessionsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<SessionRecord> records = sessionRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<SessionResponse> getSessionsByYear(Integer year, Pageable pageable) {
        Page<SessionRecord> records = sessionRecordRepository.findByYearOrderByDateCreatedDesc(year, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<SessionResponse> getSessionsByType(String sessionType, Pageable pageable) {
        Page<SessionRecord> records = sessionRecordRepository.findBySessionTypeOrderByDateCreatedDesc(sessionType, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<SessionResponse> getLatestSession() {
        Optional<SessionRecord> record = sessionRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
