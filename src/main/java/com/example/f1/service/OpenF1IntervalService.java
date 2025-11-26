package com.example.f1.service;

import com.example.f1.dto.IntervalDto;
import com.example.f1.entity.IntervalRecord;
import com.example.f1.repository.IntervalRecordRepository;
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
public class OpenF1IntervalService {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1IntervalService.class);

    private final IntervalRecordRepository repository;
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final Long defaultMeetingKey;
    private final Integer defaultDriverNumber;

    public OpenF1IntervalService(
            IntervalRecordRepository repository,
            @Value("${openf1.api.base-url:https://api.openf1.org/v1}") String baseUrl,
            @Value("${openf1.intervals.default-meeting-key:1208}") Long defaultMeetingKey,
            @Value("${openf1.intervals.default-driver-number:55}") Integer defaultDriverNumber) {
        this.repository = repository;
        this.baseUrl = baseUrl;
        this.defaultMeetingKey = defaultMeetingKey;
        this.defaultDriverNumber = defaultDriverNumber;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());
        this.restTemplate = new RestTemplate(factory);
    }

    public List<IntervalRecord> fetchAndSave(Integer driverNumber, Long meetingKey, Long sessionKey) {
        Long resolvedMeeting = resolveMeetingKey(meetingKey);
        Integer resolvedDriver = resolveDriverNumber(driverNumber);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + "/intervals")
                .queryParam("meeting_key", resolvedMeeting)
                .queryParam("driver_number", resolvedDriver);

        if (sessionKey != null) {
            builder.queryParam("session_key", sessionKey);
        }

        String uri = builder.toUriString();

        try {
            ResponseEntity<IntervalDto[]> response = restTemplate.getForEntity(uri, IntervalDto[].class);
            IntervalDto[] body = response.getBody();

            if (body == null || body.length == 0) {
                logger.info("OpenF1 intervals returned no data for meetingKey={} driver={}", resolvedMeeting, resolvedDriver);
                return Collections.emptyList();
            }

            List<IntervalRecord> records = Arrays.stream(body)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository.saveAll(records);
        } catch (RestClientException ex) {
            logger.warn("OpenF1 intervals request failed for meetingKey={} driver={}", resolvedMeeting, resolvedDriver, ex);
            return Collections.emptyList();
        }
    }

    public Optional<IntervalRecord> getLatest(Integer driverNumber, Long meetingKey) {
        return repository.findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
    }

    public List<IntervalRecord> getLatestBatch(Integer driverNumber, Long meetingKey, int limit) {
        List<IntervalRecord> records = repository.findTop30ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
        int safeLimit = Math.min(Math.max(limit, 1), records.size());
        return records.stream().limit(safeLimit).collect(Collectors.toList());
    }

    private IntervalRecord toEntity(IntervalDto dto) {
        IntervalRecord record = new IntervalRecord();
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setDriverNumber(dto.getDriverNumber());
        record.setLapNumber(dto.getLapNumber());
        record.setIntervalNumber(dto.getIntervalNumber());
        record.setIntervalType(dto.getIntervalType());
        if (dto.getStartTime() != null) {
            record.setStartTime(dto.getStartTime().toLocalDateTime());
        }
        if (dto.getEndTime() != null) {
            record.setEndTime(dto.getEndTime().toLocalDateTime());
        }
        record.setIntervalDuration(dto.getIntervalDuration());
        return record;
    }

    private Long resolveMeetingKey(Long meetingKey) {
        return meetingKey != null ? meetingKey : defaultMeetingKey;
    }

    private Integer resolveDriverNumber(Integer driverNumber) {
        return driverNumber != null ? driverNumber : defaultDriverNumber;
    }
}

