package com.example.f1.repository;

import com.example.f1.entity.SessionResultRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionResultRecordRepository extends JpaRepository<SessionResultRecord, Long> {
    
    @Query("SELECT s FROM SessionResultRecord s WHERE s.meetingKey = :meetingKey ORDER BY s.position ASC")
    Page<SessionResultRecord> findByMeetingKeyOrderByPosition(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT s FROM SessionResultRecord s WHERE s.sessionKey = :sessionKey ORDER BY s.position ASC")
    Page<SessionResultRecord> findBySessionKeyOrderByPosition(@Param("sessionKey") Integer sessionKey, Pageable pageable);
    
    @Query("SELECT s FROM SessionResultRecord s WHERE s.driverNumber = :driverNumber ORDER BY s.dateCreated DESC")
    Page<SessionResultRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    Optional<SessionResultRecord> findTopByOrderByDateCreatedDesc();
}
