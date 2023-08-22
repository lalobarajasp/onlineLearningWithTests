package com.example.onlineLearning.quiz.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {
    private  Long id;
    private  String course;
    private  String title;
    private  String questions;
    private  Quiz quiz;

    @BeforeEach
    public void setUp() {
        id = 1L;
        course = "Java";
        title = "Spring Data";
        questions = "How do you define Spring Data?";
        quiz =  new Quiz(id, course, title, questions);
    }

    @Test
    public void testQuizFields() {
        assertEquals(id, quiz.getId());
        assertEquals(course, quiz.getCourse());
        assertEquals(title, quiz.getTitle());
        assertEquals(questions, quiz.getQuestions());
    }
    @Test
    public void testQuizSetterAndGetters() {
        quiz.setId(id);
        quiz.setCourse(course);
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        assertEquals(id, quiz.getId());
        assertEquals(course, quiz.getCourse());
        assertEquals(title, quiz.getTitle());
        assertEquals(questions, quiz.getQuestions());
    }

    @Test
    public void testEmptyConstructor() {
        Quiz quiz1 = new Quiz();
        assertEquals(null, quiz1.getId());
        assertEquals(null, quiz1.getCourse());
        assertEquals(null, quiz1.getTitle());
        assertEquals(null, quiz1.getQuestions());
    }


}