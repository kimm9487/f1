package com.example.f1.repository;

import com.example.f1.entity.DriverInfoRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverInfoRecordRepository extends JpaRepository<DriverInfoRecord, Long> {

    Optional<DriverInfoRecord> findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);

    List<DriverInfoRecord> findTop20ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);
}

