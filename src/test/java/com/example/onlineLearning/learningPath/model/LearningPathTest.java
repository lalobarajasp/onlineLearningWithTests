package com.example.onlineLearning.learningPath.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LearningPathTest {

    @Test
    public void testLearningPathFields() {
        Long id = 1L;
        String typePath = "Custom";
        LearningPath learningPath = new LearningPath(id, typePath);
        assertEquals(id, learningPath.getId());
        assertEquals(typePath, learningPath.getTypePath());
    }

    @Test
    public void testLearningPathSetterAndGetters() {
        LearningPath learningPath = new LearningPath();
        Long id = 1L;
        String typePath = "Custom";

        learningPath.setId(id);
        learningPath.setTypePath(typePath);

        assertEquals(id, learningPath.getId());
        assertEquals(typePath, learningPath.getTypePath());
    }

    @Test
    public void testEmptyConstructor() {
        LearningPath learningPath = new LearningPath();

        assertEquals(null, learningPath.getId());
        assertEquals(null, learningPath.getTypePath());
    }

    @Test
    public void testParameterizedConstructor() {
        Long id = 1L;
        String typePath = "Custom";
        LearningPath learningPath = new LearningPath(id, typePath);

        assertEquals(id, learningPath.getId());
        assertEquals(typePath, learningPath.getTypePath());
    }
}
