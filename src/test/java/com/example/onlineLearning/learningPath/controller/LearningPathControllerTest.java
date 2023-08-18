package com.example.onlineLearning.learningPath.controller;

import com.example.onlineLearning.enrollment.model.Enrollment;
import com.example.onlineLearning.enrollment.service.EnrollmentService;
import com.example.onlineLearning.learningPath.model.LearningPath;

import com.example.onlineLearning.learningPath.service.LearningPathService;
import com.example.onlineLearning.payment.controller.PaymentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(LearningPathController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LearningPathControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LearningPathService learningPathService;
    private LearningPath learningPath1;
    private LearningPath learningPath2;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String typePath = "Custom";
        learningPath1 = new LearningPath(id, typePath);

        Long id2 = 2L;
        String typePath2 = "Custom";
        learningPath2 = new LearningPath(id2, typePath2);
    }

    /**
     * We use .content("{}") because
     * The purpose of this test is to focus on the behavior of the controller method,
     * not the content of the objects being processed.
     */
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateLearningPath() throws Exception {
        when(learningPathService.create(any(LearningPath.class))).thenReturn(learningPath1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/learning/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(learningPathService, times(1)).create(any(LearningPath.class));
    }

    /**
     * "$" in the  .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
     * means you're referring to the root of the JSON document.
     */
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllLearningPaths() throws Exception {
        List<LearningPath> learningPaths = new ArrayList<>();
        learningPaths.add(learningPath1);
        learningPaths.add(learningPath2);

        when(learningPathService.getAllLearningPath()).thenReturn(learningPaths);

        mockMvc.perform(MockMvcRequestBuilders.get("/learning/paths"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(learningPathService, times(1)).getAllLearningPath();
    }

    @Test
    void testGetLearningPath() throws Exception {
        Long pathId = 1L;
        when(learningPathService.getOnlyLearningPath(pathId)).thenReturn(learningPath1);

        mockMvc.perform(MockMvcRequestBuilders.get("/learning/path/{pathId}", pathId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(learningPathService, times(1)).getOnlyLearningPath(pathId);
    }

    @Test
    void testUpdateLearningPath() throws Exception {
        Long pathId = 1L;
        when(learningPathService.updateLearningPath(eq(pathId), anyString())).thenReturn(learningPath1);

        mockMvc.perform(MockMvcRequestBuilders.put("/learning/path/{pathId}", pathId)
                        .param("typePath", "newType"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(learningPathService, times(1)).updateLearningPath(eq(pathId), anyString());
    }

    @Test
    void testDeleteLearningPath() throws Exception {
        Long learningId = 1L;
        when(learningPathService.deleteLearningPath(learningId)).thenReturn(learningPath1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/learning/learning/{learningId}", learningId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(learningPathService, times(1)).deleteLearningPath(learningId);
    }







}
