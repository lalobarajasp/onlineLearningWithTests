package com.example.onlineLearning.quiz.service;

import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    //Retrieve all Quizzes
    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    //Retrieve Only a Quiz
    public Quiz getOnlyQuiz(Long id) {
        return quizRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("The quiz with id: " + id +" doesn't exist.")
        );
    }
}
