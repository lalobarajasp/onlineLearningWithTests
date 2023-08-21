package com.example.onlineLearning.tracking.controller;

import com.example.onlineLearning.quiz.controller.QuizController;
import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.service.QuizService;
import com.example.onlineLearning.tracking.model.Tracking;
import com.example.onlineLearning.tracking.service.TrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(TrackingController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
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
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getTrack() throws Exception {
        Long pathId = 1L;
        when(trackingService.getOnlyTrack(pathId)).thenReturn(tracking);
        mockMvc.perform(MockMvcRequestBuilders.get("/track/track/{trackId}", pathId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(trackingService, times(1)).getOnlyTrack(pathId);
    }
}