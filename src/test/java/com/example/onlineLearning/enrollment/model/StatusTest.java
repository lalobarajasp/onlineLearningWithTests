package com.example.onlineLearning.enrollment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {
    @Test
    public void testEnumValues() {
        assertEquals("ENROLL", Status.ENROLL.name());
        assertEquals("UNENROLL", Status.UNENROLL.name());
    }

    @Test
    public void testEnumBehavior() {
        assertEquals(Status.ENROLL, Status.valueOf("ENROLL"));
        assertEquals(Status.UNENROLL, Status.valueOf("UNENROLL"));
    }
}
