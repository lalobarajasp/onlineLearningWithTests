package com.example.onlineLearning.learningPath.repository;

import com.example.onlineLearning.learningPath.model.LearningPath;
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
public class LearningPathRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LearningPathRepository learningPathRepository;
    private LearningPath learningPath;

    @BeforeEach
    public void setUp() {
        Long id = 1L;
        String typePath = "Custom";
        learningPath = new LearningPath(id, typePath);
    }

    @Test
    public void testSaveLearningPath() {
        Long id = 2L;
        String typePath = "Custom";
        LearningPath savedLearningPath = learningPathRepository.save(learningPath);
        assertNotNull(savedLearningPath.getId());
        assertEquals(id, savedLearningPath.getId());
        assertEquals(typePath, savedLearningPath.getTypePath());
    }

    @Test
    public void testFindLearningPathById() {
        Long id = 1L;
        String typePath = "Custom";
        LearningPath savedLearningPath = learningPathRepository.save(learningPath);

        Long savedLearningPathId = savedLearningPath.getId();
        LearningPath retrievedLearningPath = learningPathRepository.findById(savedLearningPathId).orElse(null);

        assertNotNull(retrievedLearningPath);
        assertEquals(id, retrievedLearningPath.getId());
        assertEquals(typePath, retrievedLearningPath.getTypePath());
    }

}
