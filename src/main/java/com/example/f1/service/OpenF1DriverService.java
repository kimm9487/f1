package com.example.f1.service;

import com.example.f1.dto.DriverDto;
import com.example.f1.entity.DriverInfoRecord;
import com.example.f1.repository.DriverInfoRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenF1DriverService {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1DriverService.class);

    private final DriverInfoRecordRepository repository;
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final Long defaultMeetingKey;
    private final Integer defaultDriverNumber;

    public OpenF1DriverService(
            DriverInfoRecordRepository repository,
            @Value("${openf1.api.base-url:https://api.openf1.org/v1}") String baseUrl,
            @Value("${openf1.driver.default-meeting-key:1208}") Long defaultMeetingKey,
            @Value("${openf1.driver.default-driver-number:55}") Integer defaultDriverNumber) {
        this.repository = repository;
        this.baseUrl = baseUrl;
        this.defaultMeetingKey = defaultMeetingKey;
        this.defaultDriverNumber = defaultDriverNumber;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());
        this.restTemplate = new RestTemplate(factory);
    }

    public List<DriverInfoRecord> fetchAndSave(Integer driverNumber, Long meetingKey, Long sessionKey) {
        Long resolvedMeeting = resolveMeetingKey(meetingKey);
        Integer resolvedDriver = resolveDriverNumber(driverNumber);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + "/drivers")
                .queryParam("meeting_key", resolvedMeeting)
                .queryParam("driver_number", resolvedDriver);

        if (sessionKey != null) {
            builder.queryParam("session_key", sessionKey);
        }

        String uri = builder.toUriString();

        try {
            ResponseEntity<DriverDto[]> response = restTemplate.getForEntity(uri, DriverDto[].class);
            DriverDto[] body = response.getBody();
            if (body == null || body.length == 0) {
                logger.info("OpenF1 drivers returned empty for meetingKey={} driver={}", resolvedMeeting, resolvedDriver);
                return Collections.emptyList();
            }

            List<DriverInfoRecord> records = Arrays.stream(body)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository.saveAll(records);
        } catch (RestClientException ex) {
            logger.warn("OpenF1 drivers request failed for meetingKey={} driver={}", resolvedMeeting, resolvedDriver, ex);
            return Collections.emptyList();
        }
    }

    public Optional<DriverInfoRecord> getLatest(Integer driverNumber, Long meetingKey) {
        return repository.findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(
                resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
    }

    public List<DriverInfoRecord> getLatestBatch(Integer driverNumber, Long meetingKey, int limit) {
        List<DriverInfoRecord> records = repository.findTop20ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(
                resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
        int safeLimit = Math.min(Math.max(limit, 1), records.size());
        return records.stream().limit(safeLimit).collect(Collectors.toList());
    }

    private DriverInfoRecord toEntity(DriverDto dto) {
        DriverInfoRecord record = new DriverInfoRecord();
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setDriverNumber(dto.getDriverNumber());
        record.setBroadcastName(dto.getBroadcastName());
        record.setFirstName(dto.getFirstName());
        record.setLastName(dto.getLastName());
        record.setFullName(dto.getFullName());
        record.setCountryCode(dto.getCountryCode());
        record.setTeamName(dto.getTeamName());
        record.setTeamColour(dto.getTeamColour());
        record.setHeadshotUrl(dto.getHeadshotUrl());
        record.setNameAcronym(dto.getNameAcronym());
        return record;
    }

    private Long resolveMeetingKey(Long meetingKey) {
        return meetingKey != null ? meetingKey : defaultMeetingKey;
    }

    private Integer resolveDriverNumber(Integer driverNumber) {
        return driverNumber != null ? driverNumber : defaultDriverNumber;
    }
}

