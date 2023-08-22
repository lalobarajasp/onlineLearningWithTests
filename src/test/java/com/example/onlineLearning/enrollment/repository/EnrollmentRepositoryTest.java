package com.example.onlineLearning.enrollment.repository;
import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EnrollmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    private Enrollment enrollment;

    @BeforeEach
    public void setUp() {
        enrollment = new Enrollment(1L, 2L, Status.ENROLL);
        entityManager.persistAndFlush(enrollment);
    }

    @Test
    public void testSaveEnrollment() {
        Long userId = 1L;
        Long courseId = 2L;
        Status status = Status.ENROLL;
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        assertNotNull(savedEnrollment.getId());
        assertEquals(userId, savedEnrollment.getUser_id());
        assertEquals(courseId, savedEnrollment.getCourse_id());
        assertEquals(status, savedEnrollment.getStatus());
    }

    @Test
    public void testFindEnrollmentById() {
        Long userId = 1L;
        Long courseId = 2L;
        Status status = Status.ENROLL;
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        Long savedEnrollmentId = savedEnrollment.getId();
        Enrollment retrievedEnrollment = enrollmentRepository.findById(savedEnrollmentId).orElse(null);

        assertNotNull(retrievedEnrollment);
        assertEquals(userId, retrievedEnrollment.getUser_id());
        assertEquals(courseId, retrievedEnrollment.getCourse_id());
        assertEquals(status, retrievedEnrollment.getStatus());
    }

    @Test
    public void testUpdateEnrollment() {
        Long userId = 1L;
        Long courseId = 2L;

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        Long savedEnrollmentId = savedEnrollment.getId();

        // Update status
        savedEnrollment.setStatus(Status.UNENROLL);
        enrollmentRepository.save(savedEnrollment);

        Enrollment updatedEnrollment = enrollmentRepository.findById(savedEnrollmentId).orElse(null);

        assertNotNull(updatedEnrollment);
        assertEquals(userId, updatedEnrollment.getUser_id());
        assertEquals(courseId, updatedEnrollment.getCourse_id());
        assertEquals(Status.UNENROLL, updatedEnrollment.getStatus());
    }

    @Test
    public void testDeleteEnrollment() {
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        Long savedEnrollmentId = savedEnrollment.getId();
        enrollmentRepository.delete(savedEnrollment);
        equals(enrollmentRepository.existsById(savedEnrollmentId));
    }














}
