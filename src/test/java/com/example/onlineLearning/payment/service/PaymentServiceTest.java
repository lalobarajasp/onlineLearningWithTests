package com.example.onlineLearning.payment.service;

import com.example.onlineLearning.payment.model.Payment;
import com.example.onlineLearning.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment1;
    private Payment payment2;

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
    public void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));
        // Act
        List<Payment> payments = paymentService.getAllPayments();
        // Assert
        assertEquals(2, payments.size());

    }

    @Test
    public void testGetOnlyPaymentExists() {
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment1));
        // Act
        Payment actualPayment = paymentService.getOnlyPayment(paymentId);

        // Assert
        assertEquals(payment1, actualPayment);
    }

    @Test
    public void testGetOnlyPaymentNotFound() {
        // Arrange
        Long nonExistentPaymentId = 9L;
        when(paymentRepository.findById(nonExistentPaymentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> paymentService.getOnlyPayment(nonExistentPaymentId));
    }



}