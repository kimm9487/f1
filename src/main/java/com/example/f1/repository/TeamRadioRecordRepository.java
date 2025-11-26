package com.example.f1.repository;

import com.example.f1.entity.TeamRadioRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRadioRecordRepository extends JpaRepository<TeamRadioRecord, Long> {
    
    @Query("SELECT t FROM TeamRadioRecord t WHERE t.meetingKey = :meetingKey ORDER BY t.dateCreated DESC")
    Page<TeamRadioRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT t FROM TeamRadioRecord t WHERE t.driverNumber = :driverNumber ORDER BY t.dateCreated DESC")
    Page<TeamRadioRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    @Query("SELECT t FROM TeamRadioRecord t WHERE t.sessionKey = :sessionKey ORDER BY t.dateCreated DESC")
    Page<TeamRadioRecord> findBySessionKeyOrderByDateCreatedDesc(@Param("sessionKey") Integer sessionKey, Pageable pageable);
    
    Optional<TeamRadioRecord> findTopByOrderByDateCreatedDesc();
}
