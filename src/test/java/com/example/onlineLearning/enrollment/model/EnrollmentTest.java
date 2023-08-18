package com.example.onlineLearning.enrollment.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrollmentTest {

    @Test
    public void testEnrollmentFields() {
        Long user_id = 1L;
        Long course_id = 1L;
        Status status = Status.ENROLL;

        Enrollment enrollment = new Enrollment(user_id, course_id, status);
        assertEquals(user_id, enrollment.getUser_id());
        assertEquals(course_id, enrollment.getCourse_id());
        assertEquals(status, enrollment.getStatus());
    }

    @Test
    public void testEnrollmentSetterAndGetters() {
        Enrollment enrollment = new Enrollment();
        Long user_id = 3L;
        Long course_id = 4L;
        Status status = Status.ENROLL;

        enrollment.setUser_id(user_id);
        enrollment.setCourse_id(course_id);
        enrollment.setStatus(status);

        assertEquals(user_id, enrollment.getUser_id());
        assertEquals(course_id, enrollment.getCourse_id());
        assertEquals(status, enrollment.getStatus());
    }

    @Test
    public void testEmptyConstructor() {
        Enrollment enrollment = new Enrollment();

        // Ensure that fields are initialized to default values
        assertEquals(null, enrollment.getId());
        assertEquals(null, enrollment.getUser_id());
        assertEquals(null, enrollment.getCourse_id());
        assertEquals(null, enrollment.getStatus());
    }

    @Test
    public void testParameterizedConstructor() {
        Long userId = 1L;
        Long courseId = 2L;
        Status status = Status.ENROLL;

        Enrollment enrollment = new Enrollment(userId, courseId, status);

        // Ensure that fields are initialized with the provided values
        assertEquals(userId, enrollment.getUser_id());
        assertEquals(courseId, enrollment.getCourse_id());
        assertEquals(status, enrollment.getStatus());
    }










}
