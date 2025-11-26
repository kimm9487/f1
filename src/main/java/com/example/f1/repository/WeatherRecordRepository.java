package com.example.f1.repository;

import com.example.f1.entity.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {

    Optional<WeatherRecord> findFirstByMeetingKeyOrderByRecordedAtDesc(Long meetingKey);

    List<WeatherRecord> findTop20ByMeetingKeyOrderByRecordedAtDesc(Long meetingKey);
}

