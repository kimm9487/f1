package com.example.f1.repository;

import com.example.f1.entity.PitRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PitRecordRepository extends JpaRepository<PitRecord, Long> {
    
    @Query("SELECT p FROM PitRecord p WHERE p.meetingKey = :meetingKey ORDER BY p.dateCreated DESC")
    Page<PitRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT p FROM PitRecord p WHERE p.driverNumber = :driverNumber ORDER BY p.dateCreated DESC")
    Page<PitRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    Optional<PitRecord> findTopByOrderByDateCreatedDesc();
}
