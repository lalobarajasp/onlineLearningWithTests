package com.example.onlineLearning.quiz.repository;

import com.example.onlineLearning.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
