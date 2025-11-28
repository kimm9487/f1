package com.example.f1.service;

import com.example.f1.dto.WeatherDto;
import com.example.f1.entity.WeatherRecord;
import com.example.f1.repository.WeatherRecordRepository;
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
public class OpenF1WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(OpenF1WeatherService.class);

    private final WeatherRecordRepository weatherRecordRepository;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public OpenF1WeatherService(
            WeatherRecordRepository weatherRecordRepository,
            @Value("${openf1.api.base-url:https://api.openf1.org/v1}") String baseUrl) {
        this.weatherRecordRepository = weatherRecordRepository;
        this.baseUrl = baseUrl;

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) Duration.ofSeconds(5).toMillis());
        requestFactory.setReadTimeout((int) Duration.ofSeconds(10).toMillis());
        this.restTemplate = new RestTemplate(requestFactory);
    }

    public List<WeatherRecord> fetchAndSaveWeather(Long meetingKey) {
        if (meetingKey == null) {
            logger.warn("meetingKey is required to fetch OpenF1 weather data");
            return Collections.emptyList();
        }

        String uri = UriComponentsBuilder
                .fromUriString(baseUrl + "/weather")
                .queryParam("meeting_key", meetingKey)
                .toUriString();

        try {
            ResponseEntity<WeatherDto[]> response = restTemplate.getForEntity(uri, WeatherDto[].class);
            WeatherDto[] body = response.getBody();

            if (body == null || body.length == 0) {
                logger.info("OpenF1 returned no weather data for meetingKey={}", meetingKey);
                return Collections.emptyList();
            }

            List<WeatherRecord> records = Arrays.stream(body)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return weatherRecordRepository.saveAll(records);
        } catch (RestClientException ex) {
            logger.warn("OpenF1 weather request failed for meetingKey={}", meetingKey, ex);
            return Collections.emptyList();
        }
    }

    public List<WeatherRecord> getLatestRecords(Long meetingKey, int limit) {
        if (meetingKey == null) {
            return Collections.emptyList();
        }

        int safeLimit = Math.min(Math.max(limit, 1), 50);
        List<WeatherRecord> records = weatherRecordRepository.findTop20ByMeetingKeyOrderByRecordedAtDesc(meetingKey);

        if (records.isEmpty()) {
            return Collections.emptyList();
        }

        return records.stream()
                .limit(safeLimit)
                .collect(Collectors.toList());
    }

    public Optional<WeatherRecord> getLatestRecord(Long meetingKey) {
        if (meetingKey == null) {
            return Optional.empty();
        }
        return weatherRecordRepository.findFirstByMeetingKeyOrderByRecordedAtDesc(meetingKey);
    }

    private WeatherRecord toEntity(WeatherDto dto) {
        WeatherRecord record = new WeatherRecord();
        record.setMeetingKey(dto.getMeetingKey());
        record.setSessionKey(dto.getSessionKey());
        if (dto.getDate() != null) {
            record.setRecordedAt(dto.getDate().toLocalDateTime());
        }
        record.setAirTemperature(dto.getAirTemperature());
        record.setTrackTemperature(dto.getTrackTemperature());
        record.setWindSpeed(dto.getWindSpeed());
        record.setWindDirection(dto.getWindDirection());
        record.setHumidity(dto.getHumidity());
        record.setPressure(dto.getPressure());
        record.setRainfall(dto.getRainfall());

        return record;
    }
}

