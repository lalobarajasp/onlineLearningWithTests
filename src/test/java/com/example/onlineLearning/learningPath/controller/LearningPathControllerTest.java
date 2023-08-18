package com.example.onlineLearning.learningPath.controller;

import com.example.onlineLearning.learningPath.model.LearningPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@WebMvcTest(LearningPathController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class LearningPathControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Mock
//    private LearningPathServiceTest learningPathService;
//    @Autowired
//    private ObjectMapper objectMapper;
//    private LearningPath learningPath;
//
//
//    @BeforeEach
//    public void setUp() {
//        Long id = 1L;
//        String typePath = "Custom";
//        learningPath = new LearningPath(id, typePath);
//        when(learningPathService.create(any(LearningPath.class))).thenReturn(learningPath);
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testSaveLearningPath() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/learning/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(learningPath)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//    }



}
