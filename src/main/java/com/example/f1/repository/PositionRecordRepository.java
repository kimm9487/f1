package com.example.f1.repository;

import com.example.f1.entity.PositionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRecordRepository extends JpaRepository<PositionRecord, Long> {
    
    @Query("SELECT p FROM PositionRecord p WHERE p.meetingKey = :meetingKey ORDER BY p.dateCreated DESC")
    Page<PositionRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT p FROM PositionRecord p WHERE p.driverNumber = :driverNumber ORDER BY p.dateCreated DESC")
    Page<PositionRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    Optional<PositionRecord> findTopByOrderByDateCreatedDesc();
}
