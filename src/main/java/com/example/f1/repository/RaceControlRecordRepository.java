package com.example.f1.repository;

import com.example.f1.entity.RaceControlRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceControlRecordRepository extends JpaRepository<RaceControlRecord, Long> {
    
    @Query("SELECT r FROM RaceControlRecord r WHERE r.meetingKey = :meetingKey ORDER BY r.dateCreated DESC")
    Page<RaceControlRecord> findByMeetingKeyOrderByDateCreatedDesc(@Param("meetingKey") Integer meetingKey, Pageable pageable);
    
    @Query("SELECT r FROM RaceControlRecord r WHERE r.category = :category ORDER BY r.dateCreated DESC")
    Page<RaceControlRecord> findByCategoryOrderByDateCreatedDesc(@Param("category") String category, Pageable pageable);
    
    Optional<RaceControlRecord> findTopByOrderByDateCreatedDesc();
}
