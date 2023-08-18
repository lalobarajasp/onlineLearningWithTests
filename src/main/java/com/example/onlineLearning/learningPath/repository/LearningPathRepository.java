package com.example.onlineLearning.learningPath.repository;

import com.example.onlineLearning.learningPath.model.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningPathRepository extends JpaRepository<LearningPath,Long> {
}
