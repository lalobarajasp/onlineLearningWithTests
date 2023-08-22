package com.example.onlineLearning.tracking.repository;

import com.example.onlineLearning.tracking.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

}
