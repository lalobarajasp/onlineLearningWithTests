package com.example.onlineLearning.tracking.service;

import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.tracking.model.Tracking;
import com.example.onlineLearning.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {
    @Autowired
    private TrackingRepository trackingRepository;

    //Retrieve Only a Track
    public Tracking getOnlyTrack(Long id) {
        return trackingRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("The track with id: " + id +" doesn't exist.")
        );
    }
}
