package com.example.onlineLearning.quiz.service;

import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;
    @InjectMocks
    private QuizService quizService;
    private Quiz quiz;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String course = "Java";
        String title = "Spring Data";
        String questions = "How do you define Spring Data?";
        quiz =  new Quiz(id, course, title, questions);
    }

    @Test
    public void getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        when(quizRepository.findAll()).thenReturn(quizzes);
        List<Quiz> resultQuiz = quizService.getAllQuizzes();
        assertEquals(quizzes, resultQuiz);
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void getOnlyQuiz() {
        Long id = 1L;
        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));
        Quiz resultQuiz = quizService.getOnlyQuiz(id);
        assertEquals(quiz, resultQuiz);
    }

    @Test
    public void testGetOnlyQuizNonExistingId() {
        Long id = 1L;
        when(quizRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> quizService.getOnlyQuiz(id),
                "The quiz with id: " + id +" doesn't exist.");
    }

}