package com.example.f1.service;

import com.example.f1.dto.CarDataDto;
import com.example.f1.entity.CarDataRecord;
import com.example.f1.repository.CarDataRecordRepository;
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
public class OpenF1CarDataService {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1CarDataService.class);

    private final CarDataRecordRepository repository;
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final Long defaultMeetingKey;
    private final Integer defaultDriverNumber;

    public OpenF1CarDataService(
            CarDataRecordRepository repository,
            @Value("${openf1.api.base-url:https://api.openf1.org/v1}") String baseUrl,
            @Value("${openf1.cardata.default-meeting-key:1208}") Long defaultMeetingKey,
            @Value("${openf1.cardata.default-driver-number:55}") Integer defaultDriverNumber) {
        this.repository = repository;
        this.baseUrl = baseUrl;
        this.defaultMeetingKey = defaultMeetingKey;
        this.defaultDriverNumber = defaultDriverNumber;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());
        this.restTemplate = new RestTemplate(factory);
    }

    public List<CarDataRecord> fetchAndSave(Integer driverNumber, Long meetingKey, Long sessionKey) {
        Long resolvedMeetingKey = resolveMeetingKey(meetingKey);
        Integer resolvedDriver = resolveDriverNumber(driverNumber);

        String uri = UriComponentsBuilder.fromUriString(baseUrl + "/car_data")
                .queryParam("meeting_key", resolvedMeetingKey)
                .queryParam("driver_number", resolvedDriver)
                .queryParamIfPresent("session_key", Optional.ofNullable(sessionKey))
                .toUriString();

        try {
            ResponseEntity<CarDataDto[]> response = restTemplate.getForEntity(uri, CarDataDto[].class);
            CarDataDto[] body = response.getBody();
            if (body == null || body.length == 0) {
                logger.info("OpenF1 car data returned empty for meetingKey={} driver={}", resolvedMeetingKey, resolvedDriver);
                return Collections.emptyList();
            }

            List<CarDataRecord> records = Arrays.stream(body)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository.saveAll(records);
        } catch (RestClientException ex) {
            logger.warn("OpenF1 car_data request failed for meetingKey={} driver={}", resolvedMeetingKey, resolvedDriver, ex);
            return Collections.emptyList();
        }
    }

    public Optional<CarDataRecord> getLatest(Integer driverNumber, Long meetingKey) {
        return repository.findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(
                resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
    }

    public List<CarDataRecord> getLatestBatch(Integer driverNumber, Long meetingKey, int limit) {
        List<CarDataRecord> records = repository.findTop30ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(
                resolveMeetingKey(meetingKey), resolveDriverNumber(driverNumber));
        int safeLimit = Math.min(Math.max(limit, 1), records.size());
        return records.stream().limit(safeLimit).collect(Collectors.toList());
    }

    private CarDataRecord toEntity(CarDataDto dto) {
        CarDataRecord record = new CarDataRecord();
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        record.setDriverNumber(dto.getDriverNumber());
        record.setSpeed(dto.getSpeed());
        record.setThrottle(dto.getThrottle());
        record.setBrake(dto.getBrake());
        record.setRpm(dto.getRpm());
        record.setDrs(dto.getDrs());
        record.setGear(dto.getGear());
        if (dto.getDate() != null) {
            record.setRecordedAt(dto.getDate().toLocalDateTime());
        }
        return record;
    }

    private Long resolveMeetingKey(Long meetingKey) {
        return meetingKey != null ? meetingKey : defaultMeetingKey;
    }

    private Integer resolveDriverNumber(Integer driverNumber) {
        return driverNumber != null ? driverNumber : defaultDriverNumber;
    }
}

