package com.example.f1.repository;

import com.example.f1.entity.LocationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRecordRepository extends JpaRepository<LocationRecord, Long> {
    
    @Query("SELECT l FROM LocationRecord l WHERE l.meetingKey = :meetingKey ORDER BY l.dateCreated DESC")
    Page<LocationRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT l FROM LocationRecord l WHERE l.driverNumber = :driverNumber ORDER BY l.dateCreated DESC")
    Page<LocationRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    Optional<LocationRecord> findTopByOrderByDateCreatedDesc();
}
