package com.example.f1.repository;

import com.example.f1.entity.StintRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StintRecordRepository extends JpaRepository<StintRecord, Long> {
    
    @Query("SELECT s FROM StintRecord s WHERE s.meetingKey = :meetingKey ORDER BY s.dateCreated DESC")
    Page<StintRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT s FROM StintRecord s WHERE s.driverNumber = :driverNumber ORDER BY s.dateCreated DESC")
    Page<StintRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    @Query("SELECT s FROM StintRecord s WHERE s.sessionKey = :sessionKey ORDER BY s.dateCreated DESC")
    Page<StintRecord> findBySessionKeyOrderByDateCreatedDesc(@Param("sessionKey") Integer sessionKey, Pageable pageable);
    
    Optional<StintRecord> findTopByOrderByDateCreatedDesc();
}
