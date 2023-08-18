package com.example.onlineLearning.learningPath.service;

import com.example.onlineLearning.learningPath.model.LearningPath;
import com.example.onlineLearning.learningPath.repository.LearningPathRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


public class LearningPathServiceTest {

    @Mock
    private LearningPathRepository learningPathRepository;

    @InjectMocks
    private LearningPathService learningPathService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateLearningPath() {
        LearningPath learningPath = new LearningPath(/* initialize the object */);
        when(learningPathRepository.save(any(LearningPath.class))).thenReturn(learningPath);

        LearningPath createdPath = learningPathService.create(learningPath);

        assertEquals(learningPath, createdPath);
        verify(learningPathRepository, times(1)).save(learningPath);
    }

}
