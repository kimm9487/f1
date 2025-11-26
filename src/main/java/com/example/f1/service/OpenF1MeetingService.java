package com.example.f1.service;

import com.example.f1.dto.MeetingDto;
import com.example.f1.dto.MeetingResponse;
import com.example.f1.entity.MeetingRecord;
import com.example.f1.repository.MeetingRecordRepository;
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
public class OpenF1MeetingService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenF1MeetingService.class);
    
    @Autowired
    private MeetingRecordRepository meetingRecordRepository;
    
    @Value("${openf1.api.base-url}")
    private String baseUrl;
    
    @Value("${openf1.meetings.default-year}")
    private Integer defaultYear;
    
    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(10));
        
        return new RestTemplate(factory);
    }
    
    public void fetchAndSaveMeetings() {
        fetchAndSaveMeetings(defaultYear);
    }
    
    public void fetchAndSaveMeetings(Integer year) {
        try {
            RestTemplate restTemplate = createRestTemplate();
            
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(baseUrl + "/meetings");
            
            if (year != null) {
                uriBuilder.queryParam("year", year);
            }
            
            String url = uriBuilder.toUriString();
            logger.info("Fetching meetings from: {}", url);
            
            MeetingDto[] meetingArray = restTemplate.getForObject(url, MeetingDto[].class);
            
            if (meetingArray != null && meetingArray.length > 0) {
                List<MeetingRecord> records = Arrays.stream(meetingArray)
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                meetingRecordRepository.saveAll(records);
                logger.info("Saved {} meeting records", records.size());
            } else {
                logger.warn("No meeting data received from OpenF1 API");
            }
            
        } catch (Exception e) {
            logger.error("Error fetching meeting data from OpenF1: {}", e.getMessage(), e);
        }
    }
    
    private MeetingRecord convertToEntity(MeetingDto dto) {
        MeetingRecord record = new MeetingRecord();
        record.setCircuitKey(dto.getCircuitKey());
        record.setCircuitShortName(dto.getCircuitShortName());
        record.setCountryCode(dto.getCountryCode());
        record.setCountryKey(dto.getCountryKey());
        record.setCountryName(dto.getCountryName());
        record.setDateStart(dto.getDateStart());
        record.setGmtOffset(dto.getGmtOffset());
        record.setLocation(dto.getLocation());
        record.setMeetingKey(dto.getMeetingKey());
        record.setMeetingName(dto.getMeetingName());
        record.setMeetingOfficialName(dto.getMeetingOfficialName());
        record.setYear(dto.getYear());
        return record;
    }
    
    private MeetingResponse convertToResponse(MeetingRecord record) {
        MeetingResponse response = new MeetingResponse();
        response.setId(record.getId());
        response.setDateCreated(record.getDateCreated());
        response.setCircuitKey(record.getCircuitKey());
        response.setCircuitShortName(record.getCircuitShortName());
        response.setCountryCode(record.getCountryCode());
        response.setCountryKey(record.getCountryKey());
        response.setCountryName(record.getCountryName());
        response.setDateStart(record.getDateStart());
        response.setGmtOffset(record.getGmtOffset());
        response.setLocation(record.getLocation());
        response.setMeetingKey(record.getMeetingKey());
        response.setMeetingName(record.getMeetingName());
        response.setMeetingOfficialName(record.getMeetingOfficialName());
        response.setYear(record.getYear());
        return response;
    }
    
    public Page<MeetingResponse> getMeetingsByYear(Integer year, Pageable pageable) {
        Page<MeetingRecord> records = meetingRecordRepository.findByYearOrderByDateCreatedDesc(year, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Page<MeetingResponse> getMeetingsByMeetingKey(Integer meetingKey, Pageable pageable) {
        Page<MeetingRecord> records = meetingRecordRepository.findByMeetingKeyOrderByDateCreatedDesc(meetingKey, pageable);
        return records.map(this::convertToResponse);
    }
    
    public Optional<MeetingResponse> getLatestMeeting() {
        Optional<MeetingRecord> record = meetingRecordRepository.findTopByOrderByDateCreatedDesc();
        return record.map(this::convertToResponse);
    }
}
