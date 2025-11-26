package com.example.f1.repository;

import com.example.f1.entity.OvertakeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OvertakeRecordRepository extends JpaRepository<OvertakeRecord, Long> {
    
    @Query("SELECT o FROM OvertakeRecord o WHERE o.meetingKey = :meetingKey ORDER BY o.dateCreated DESC")
    Page<OvertakeRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT o FROM OvertakeRecord o WHERE o.driverNumber = :driverNumber ORDER BY o.dateCreated DESC")
    Page<OvertakeRecord> findByDriverNumberOrderByDateCreatedDesc(@Param("driverNumber") Integer driverNumber, Pageable pageable);
    
    Optional<OvertakeRecord> findTopByOrderByDateCreatedDesc();
}
