package com.example.onlineLearning.tracking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackingTest {
    private Long id;
    private String grade;
    private String average;
    private Tracking tracking;

    @BeforeEach
    public void setUp() {
        id = 1L;
        grade = "100";
        average = "95";
        tracking = new Tracking(id, grade, average);
    }

    @Test
    public void testTrackingFields() {
        assertEquals(id, tracking.getId());
        assertEquals(grade, tracking.getGrade());
        assertEquals(average, tracking.getAverage());
    }
    @Test
    public void testTrackingSetterAndGetters() {
        tracking.setId(id);
        tracking.setGrade(grade);
        tracking.setAverage(average);

        assertEquals(id, tracking.getId());
        assertEquals(grade, tracking.getGrade());
        assertEquals(average, tracking.getAverage());
    }

    @Test
    public void testEmptyConstructor() {
        Tracking tracking1 = new Tracking();
        assertEquals(null, tracking1.getId());
        assertEquals(null, tracking1.getGrade());
        assertEquals(null, tracking1.getAverage());
    }



}