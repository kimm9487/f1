package com.example.f1.repository;

import com.example.f1.entity.SessionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRecordRepository extends JpaRepository<SessionRecord, Long> {
    
    @Query("SELECT s FROM SessionRecord s WHERE s.meetingKey = :meetingKey ORDER BY s.dateCreated DESC")
    Page<SessionRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT s FROM SessionRecord s WHERE s.year = :year ORDER BY s.dateCreated DESC")
    Page<SessionRecord> findByYearOrderByDateCreatedDesc(@Param("year") Integer year, Pageable pageable);
    
    @Query("SELECT s FROM SessionRecord s WHERE s.sessionType = :sessionType ORDER BY s.dateCreated DESC")
    Page<SessionRecord> findBySessionTypeOrderByDateCreatedDesc(@Param("sessionType") String sessionType, Pageable pageable);
    
    Optional<SessionRecord> findTopByOrderByDateCreatedDesc();
}
