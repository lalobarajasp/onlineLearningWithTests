package com.example.onlineLearning.quiz.controller;

import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@WebMvcTest(QuizController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuizService quizService;
    private Quiz quiz1;
    private Quiz quiz2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String course = "Java";
        String title = "Spring Data";
        String questions = "How do you define Spring Data?";
        quiz1 =  new Quiz(id, course, title, questions);

        Long id2 = 2L;
        String course2 = "Java";
        String title2 = "Spring boot";
        String questions2 = "How do you define Spring boot?";
        quiz2 =  new Quiz(id2, course2, title2, questions2);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllQuizzes() throws Exception {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz1);
        quizzes.add(quiz2);

        when(quizService.getAllQuizzes()).thenReturn(quizzes);
        mockMvc.perform(MockMvcRequestBuilders.get("/quiz/quizzes"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(quizService, times(1)).getAllQuizzes();
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetQuiz() throws Exception {
        Long pathId = 1L;
        when(quizService.getOnlyQuiz(pathId)).thenReturn(quiz1);

        mockMvc.perform(MockMvcRequestBuilders.get("/quiz/quiz/{quizId}", pathId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(quizService, times(1)).getOnlyQuiz(pathId);
    }
}