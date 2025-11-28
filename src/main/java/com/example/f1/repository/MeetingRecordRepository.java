package com.example.f1.repository;

import com.example.f1.entity.MeetingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRecordRepository extends JpaRepository<MeetingRecord, Long> {
    
    @Query("SELECT m FROM MeetingRecord m WHERE m.year = :year ORDER BY m.dateCreated DESC")
    Page<MeetingRecord> findByYearOrderByDateCreatedDesc(@Param("year") Integer year, Pageable pageable);
    
    @Query("SELECT m FROM MeetingRecord m WHERE m.meetingKey = :meetingKey ORDER BY m.dateCreated DESC")
    Page<MeetingRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    Optional<MeetingRecord> findTopByOrderByDateCreatedDesc();
}
