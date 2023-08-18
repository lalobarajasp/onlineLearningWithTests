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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void testGetAllLearningPaths() {
        List<LearningPath> learningPaths = new ArrayList<>();
        when(learningPathRepository.findAll()).thenReturn(learningPaths);
        List<LearningPath> resultPaths = learningPathService.getAllLearningPath();
        assertEquals(learningPaths, resultPaths);
        verify(learningPathRepository, times(1)).findAll();
    }

    @Test
    public void testGetOnlyLearningPath() {
        Long pathId = 1L;
        Long id = 1L;
        String type = "Custom";
        LearningPath learningPath = new LearningPath(id, type);
        when(learningPathRepository.findById(pathId)).thenReturn(Optional.of(learningPath));
        LearningPath resultPath = learningPathService.getOnlyLearningPath(pathId);
        assertEquals(learningPath, resultPath);
    }

    @Test
    public void testUpdateLearningPath() {
        Long pathId = 1L;
        String type = "Custom";
        String newTypePath = "newType";
        LearningPath learningPath = new LearningPath(pathId, type);

        // Mock the repository behavior
        when(learningPathRepository.findById(pathId)).thenReturn(Optional.of(learningPath));
        when(learningPathRepository.save(any(LearningPath.class))).thenReturn(learningPath);

        // Call the service method
        LearningPath updatedPath = learningPathService.updateLearningPath(pathId, newTypePath);

        // Verify the assertions
        assertEquals(learningPath, updatedPath);
        assertEquals(newTypePath, updatedPath.getTypePath());

        // Verify that repository methods were called
        verify(learningPathRepository, times(1)).findById(pathId);
        verify(learningPathRepository, times(1)).save(learningPath);
    }

    @Test
    public void testDeleteLearningPath() {
        Long pathId = 1L;
        String type = "custom";
        LearningPath learningPath = new LearningPath(pathId,type);
        when(learningPathRepository.existsById(pathId)).thenReturn(true);
        when(learningPathRepository.findById(pathId)).thenReturn(Optional.of(learningPath));

        LearningPath deletedPath = learningPathService.deleteLearningPath(pathId);

        assertEquals(learningPath, deletedPath);
        verify(learningPathRepository, times(1)).existsById(pathId);
        verify(learningPathRepository, times(1)).findById(pathId);
        verify(learningPathRepository, times(1)).deleteById(pathId);
    }


}
