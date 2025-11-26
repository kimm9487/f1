package com.example.f1.repository;

import com.example.f1.entity.CarDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarDataRecordRepository extends JpaRepository<CarDataRecord, Long> {

    Optional<CarDataRecord> findFirstByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);

    List<CarDataRecord> findTop30ByMeetingKeyAndDriverNumberOrderByRecordedAtDesc(Long meetingKey, Integer driverNumber);
}

