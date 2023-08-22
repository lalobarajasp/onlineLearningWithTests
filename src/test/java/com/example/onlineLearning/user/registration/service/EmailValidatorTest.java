package com.example.onlineLearning.user.registration.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @Test
    void testValidEmail() {
        assertTrue(emailValidator.test("user@example.com"));
        assertTrue(emailValidator.test("email.d123@gmail.com"));
        assertTrue(emailValidator.test("test.email@example.com.mx"));
    }

}