package com.example.onlineLearning.payment.model;

import com.example.onlineLearning.learningPath.model.LearningPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    public void testPaymentFields() {
        Long id = 1L;
        String amount = "100";
        Payment payment = new Payment(id, amount);
        assertEquals(id, payment.getId());
        assertEquals(amount, payment.getAmount());
    }

    @Test
    public void testPaymentSetterAndGetters() {
        Payment payment = new Payment();
        Long id = 1L;
        String amount = "100";

        payment.setId(id);
        payment.setAmount(amount);

        assertEquals(id, payment.getId());
        assertEquals(amount, payment.getAmount());
    }

    @Test
    public void testEmptyConstructor() {
        Payment payment = new Payment();
        assertEquals(null, payment.getId());
        assertEquals(null, payment.getAmount());

    }

    @Test
    public void testParameterizedConstructor() {
        Long id = 1L;
        String amount = "100";
        Payment payment = new Payment(id, amount);

        assertEquals(id, payment.getId());
        assertEquals(amount, payment.getAmount());
    }

}