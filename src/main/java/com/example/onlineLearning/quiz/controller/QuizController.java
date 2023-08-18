package com.example.onlineLearning.quiz.controller;

import com.example.onlineLearning.learningPath.model.LearningPath;
import com.example.onlineLearning.learningPath.service.LearningPathService;
import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    private ResponseEntity<List<Quiz>> AllQuizzes (){
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping (path="quiz/{quizId}")
    public Quiz getQuiz(@PathVariable("quizId")Long id) {
        return quizService.getOnlyQuiz(id);
    }


}
