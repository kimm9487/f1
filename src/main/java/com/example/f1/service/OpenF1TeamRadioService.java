package com.example.f1.service;

import com.example.f1.dto.TeamRadioDto;
import com.example.f1.dto.TeamRadioResponse;
import com.example.f1.entity.TeamRadioRecord;
import com.example.f1.repository.TeamRadioRecordRepository;
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
public class OpenF1TeamRadioService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1TeamRadioService.class);
    
    @Autowired
    private TeamRadioRecordRepository teamRadioRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.team-radio.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.team-radio.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveTeamRadios() {
        fetchAndSaveTeamRadios(defaultMeetingKey, 
                              defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveTeamRadios(Integer meetingKey, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/team_radio")
                    .queryParam("meeting_key", meetingKey);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching team radio data from: {}", url);
            
            TeamRadioDto[] teamRadioArray = restTemplate.getForObject(url, TeamRadioDto[].class);
            
            if (teamRadioArray != null && teamRadioArray.length > 0) {
                List<TeamRadioRecord> records = Arrays.stream(teamRadioArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                teamRadioRecordRepository.saveAll(records);
                logger.info("Saved {} team radio records", records.size());
            } else {
                logger.warn("No team radio data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching team radio data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private TeamRadioRecord convertToEntity(TeamRadioDto dto) {
        TeamRadioRecord record = new TeamRadioRecord();
        record.setDate(dto.getDate());
        record.setDriverNumber(dto.getDriverNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setRecordingUrl(dto.getRecordingUrl());
        record.setSessionKey(dto.getSessionKey());
        return record;
    }
    
    private TeamRadioResponse convertToResponse(TeamRadioRecord record) {
        TeamRadioResponse response = new TeamRadioResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDate(record.getDate());
        response.setDriverNumber(record.getDriverNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setRecordingUrl(record.getRecordingUrl());
        response.setSessionKey(record.getSessionKey());
        return response;
    }
    
    public Page<TeamRadioResponse> getTeamRadiosByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<TeamRadioRecord> records = teamRadioRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<TeamRadioResponse> getTeamRadiosByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<TeamRadioRecord> records = teamRadioRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<TeamRadioResponse> getTeamRadiosBySessionKey(Integer sessionKey, Pageable pageable) {
        Page<TeamRadioRecord> records = teamRadioRecordRepository.findBySessionKeyOrderByDateCreatedDesc(sessionKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<TeamRadioResponse> getLatestTeamRadio() {
        Optional<TeamRadioRecord> record = teamRadioRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
