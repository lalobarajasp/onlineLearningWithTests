package com.example.onlineLearning.learningPath.repository;

import com.example.onlineLearning.learningPath.model.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath,Long> {
}
