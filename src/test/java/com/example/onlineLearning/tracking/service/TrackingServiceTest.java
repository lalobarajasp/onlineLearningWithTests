package com.example.onlineLearning.tracking.service;

import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.tracking.model.Tracking;
import com.example.onlineLearning.tracking.repository.TrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TrackingServiceTest {
    @Mock
    private TrackingRepository trackingRepository;

    @InjectMocks
    private TrackingService trackingService;
    private Tracking tracking;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String grade = "100";
        String average = "95";
        tracking = new Tracking(id, grade, average);
    }

    @Test
    void getOnlyTrack() {
        Long id = 1L;
        when(trackingRepository.findById(id)).thenReturn(Optional.of(tracking));
        Tracking resultTracking = trackingService.getOnlyTrack(id);
        assertEquals(tracking, resultTracking );
    }

    @Test
    public void testGetOnlyTrackNonExistingId() {
        Long id = 1L;
        when(trackingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> trackingService.getOnlyTrack(id),
                "The track with id: " + id +" doesn't exist.");
    }
}