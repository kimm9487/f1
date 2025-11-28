package com.example.f1.repository;

import com.example.f1.entity.IntervalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IntervalRecordRepository extends JpaRepository<IntervalRecord, Long> {

    List<IntervalRecord> findTop30ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);

    Optional<IntervalRecord> findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);
}

