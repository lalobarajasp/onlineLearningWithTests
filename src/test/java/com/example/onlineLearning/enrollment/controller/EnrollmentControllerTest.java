package com.example.onlineLearning.enrollment.controller;


import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.model.Status;
import com.example.onlineLearning.enrollment.service.EnrollmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EnrollmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EnrollmentService enrollmentService;
    private Enrollment enrollment;

    @BeforeEach
    public void setUp() {
        enrollment = new Enrollment(1L, 2L, Status.ENROLL);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testEnrollCourseController() throws Exception {
        Long userId = 1L;
        Long courseId = 2L;
        Status status = Status.ENROLL;

        Enrollment enrollmentRequest = new Enrollment(userId, courseId, status);

        // Print the enrollment JSON for debugging
        ObjectMapper objectMapper = new ObjectMapper();
        String enrollmentJson = objectMapper.writeValueAsString(enrollmentRequest);
        System.out.println("Enrollment JSON: " + enrollmentJson);

        // Mock the behavior of the service
        Enrollment enrolledEnrollment = new Enrollment(userId, courseId, status);
        enrolledEnrollment.setId(1L);
        when(enrollmentService.enrollCourse(Mockito.any(Enrollment.class))).thenReturn(enrolledEnrollment);

        // Perform the request and print response content for debugging
        MvcResult result = mockMvc.perform(post("/enrollment/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enrollmentJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Perform assertions on the response content
        assertEquals("Enroll", responseContent);

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUnEnrollController() throws Exception {
        Long enrollmentId = 1L;

        // Perform the request and expect a successful response
        mockMvc.perform(post("/enrollment/unenroll/{id}", enrollmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Unenroll"));

        // Verify the service method call
        verify(enrollmentService, times(1)).unEnrollCourse(enrollmentId);

    }



}
