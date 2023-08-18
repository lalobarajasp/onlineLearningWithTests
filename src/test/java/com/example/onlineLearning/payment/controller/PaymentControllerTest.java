package com.example.onlineLearning.payment.controller;

import com.example.onlineLearning.course.controller.CourseController;
import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.payment.model.Payment;
import com.example.onlineLearning.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;
    private Payment payment1;
    private Payment payment2;

    PaymentControllerTest() throws Exception {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String amount = "100";
        payment1 = new Payment(id, amount);

        Long id2 = 2L;
        String amount2 = "200";
        payment2 = new Payment(id2, amount2);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAllPayments() throws Exception {
        List<Payment> expectedPayments = Arrays.asList(payment1, payment2);
        when(paymentService.getAllPayments()).thenReturn(expectedPayments);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/payment/payments"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseContent = result.getResponse().getContentAsString();
        String expectedJson = "["
                + "{\"id\":" + payment1.getId() + ",\"amount\":\"" + payment1.getAmount() + "\"},"
                + "{\"id\":" + payment2.getId() + ",\"amount\":\"" + payment2.getAmount() + "\"}"
                + "]";

        System.out.println("Response Content: " + responseContent);
        System.out.println("Expected JSON: " + expectedJson);

        try {
            JSONAssert.assertEquals(expectedJson, responseContent, false);
        } catch (AssertionError e) {
            System.err.println("JSON Assertion Error: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetPayment() throws Exception {
        Long paymentId = 1L;
        when(paymentService.getOnlyPayment(paymentId)).thenReturn(payment1);
        // Act and Assert
        mockMvc.perform(get("/payment/payment/{paymentId}", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(paymentId));
    }



}