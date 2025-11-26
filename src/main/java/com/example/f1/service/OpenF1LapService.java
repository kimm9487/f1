package com.example.f1.service;

import com.example.f1.dto.LapDto;
import com.example.f1.dto.LapResponse;
import com.example.f1.entity.LapRecord;
import com.example.f1.repository.LapRecordRepository;
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
public class OpenF1LapService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1LapService.class);
    
    @Autowired
    private LapRecordRepository lapRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.laps.default-meeting-key}")
    private Integer defaultMeetingKey;
    
    @Value("${openf1.laps.default-driver-number}")
    private Integer defaultDriverNumber;
    
    @Value("${openf1.laps.default-session-key:}")
    private String defaultSessionKey;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveLaps() {
        fetchAndSaveLaps(defaultMeetingKey, defaultDriverNumber, 
                        defaultSessionKey.isEmpty() ? null : Integer.valueOf(defaultSessionKey));
    }
    
    public void fetchAndSaveLaps(Integer meetingKey, Integer driverNumber, Integer sessionKey) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/laps")
                    .queryParam("meeting_key", meetingKey)
                    .queryParam("driver_number", driverNumber);
            
            if (sessionKey != null) {
                uriBuilder.queryParam("session_key", sessionKey);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching laps from: {}", url);
            
            LapDto[] lapArray = restTemplate.getForObject(url, LapDto[].class);
            
            if (lapArray != null && lapArray.length > 0) {
                List<LapRecord> records = Arrays.stream(lapArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                lapRecordRepository.saveAll(records);
                logger.info("Saved {} lap records", records.size());
            } else {
                logger.warn("No lap data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching lap data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private LapRecord convertToEntity(LapDto dto) {
        LapRecord record = new LapRecord();
        record.setDriverNumber(dto.getDriverNumber());
        record.setDurationSector1(dto.getDurationSector1());
        record.setDurationSector2(dto.getDurationSector2());
        record.setDurationSector3(dto.getDurationSector3());
        record.setI1Speed(dto.getI1Speed());
        record.setI2Speed(dto.getI2Speed());
        record.setIsPitOutLap(dto.getIsPitOutLap());
        record.setLapDuration(dto.getLapDuration());
        record.setLapNumber(dto.getLapNumber());
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setStSpeed(dto.getStSpeed());
        return record;
    }
    
    private LapResponse convertToResponse(LapRecord record) {
        LapResponse response = new LapResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setDriverNumber(record.getDriverNumber());
        response.setDurationSector1(record.getDurationSector1());
        response.setDurationSector2(record.getDurationSector2());
        response.setDurationSector3(record.getDurationSector3());
        response.setI1Speed(record.getI1Speed());
        response.setI2Speed(record.getI2Speed());
        response.setIsPitOutLap(record.getIsPitOutLap());
        response.setLapDuration(record.getLapDuration());
        response.setLapNumber(record.getLapNumber());
        response.setMeetingKey(record.getMeetingKey());
        response.setSessionKey(record.getSessionKey());
        response.setStSpeed(record.getStSpeed());
        return response;
    }
    
    public Page<LapResponse> getLapsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<LapRecord> records = lapRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<LapResponse> getLapsByDriverNumber(Integer driverNumber, Pageable pageable) {
        Page<LapRecord> records = lapRecordRepository.findByDriverNumberOrderByDateCreatedDesc(driverNumber, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<LapResponse> getLatestLap() {
        Optional<LapRecord> record = lapRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
