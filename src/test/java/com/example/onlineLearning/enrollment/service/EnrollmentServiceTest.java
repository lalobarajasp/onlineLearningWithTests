package com.example.onlineLearning.enrollment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlineLearning.course.repository.CourseRepository;
import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.model.Status;
import com.example.onlineLearning.enrollment.repository.EnrollmentRepository;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//It tells Spring which beans and configurations should be loaded for your test.
@ContextConfiguration(classes = {EnrollmentService.class})
//It is used to register extensions (JUnit 5 extensions) that provide additional features to your tests.
@ExtendWith(SpringExtension.class)
class EnrollmentServiceTest {
    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Test
    void testEnrollCourse() {
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(courseRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(1L);
        enrollment.setId(1L);
        enrollment.setStatus(Status.ENROLL);
        enrollment.setUser_id(1L);
        when(enrollmentRepository.save(Mockito.<Enrollment>any())).thenReturn(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setCourse_id(1L);
        enrollment2.setId(1L);
        enrollment2.setStatus(Status.ENROLL);
        enrollment2.setUser_id(1L);
        Enrollment actualEnrollCourseResult = enrollmentService.enrollCourse(enrollment2);
        assertSame(enrollment2, actualEnrollCourseResult);
        assertEquals(Status.ENROLL, actualEnrollCourseResult.getStatus());
        verify(appUserRepository).existsById(Mockito.<Long>any());
        verify(courseRepository).existsById(Mockito.<Long>any());
        verify(enrollmentRepository).save(Mockito.<Enrollment>any());
    }

    @Test
    void testEnrollCourse2() {
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        when(courseRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(1L);
        enrollment.setId(1L);
        enrollment.setStatus(Status.ENROLL);
        enrollment.setUser_id(1L);
        assertNull(enrollmentService.enrollCourse(enrollment));
        verify(appUserRepository).existsById(Mockito.<Long>any());
    }

    @Test
    void testEnrollCourse3() {
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(courseRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(1L);
        enrollment.setId(1L);
        enrollment.setStatus(Status.ENROLL);
        enrollment.setUser_id(1L);
        assertNull(enrollmentService.enrollCourse(enrollment));
        verify(appUserRepository).existsById(Mockito.<Long>any());
        verify(courseRepository).existsById(Mockito.<Long>any());
    }

    @Test
    void testUnEnrollCourse() {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(1L);
        enrollment.setId(1L);
        enrollment.setStatus(Status.ENROLL);
        enrollment.setUser_id(1L);
        Optional<Enrollment> ofResult = Optional.of(enrollment);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setCourse_id(1L);
        enrollment2.setId(1L);
        enrollment2.setStatus(Status.ENROLL);
        enrollment2.setUser_id(1L);
        when(enrollmentRepository.save(Mockito.<Enrollment>any())).thenReturn(enrollment2);
        when(enrollmentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(enrollmentRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        Enrollment actualUnEnrollCourseResult = enrollmentService.unEnrollCourse(1L);
        assertSame(enrollment, actualUnEnrollCourseResult);
        assertEquals(Status.UNENROLL, actualUnEnrollCourseResult.getStatus());
        verify(enrollmentRepository).existsById(Mockito.<Long>any());
        verify(enrollmentRepository).save(Mockito.<Enrollment>any());
        verify(enrollmentRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testUnEnrollCourse3() {
        when(enrollmentRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        assertNull(enrollmentService.unEnrollCourse(1L));
        verify(enrollmentRepository).existsById(Mockito.<Long>any());
    }
}

