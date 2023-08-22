package com.example.onlineLearning.user.registration.model;

import com.example.onlineLearning.user.appUser.model.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationRequestTest {
    RegistrationRequest request1;
    RegistrationRequest request2;

    @BeforeEach
    public void setUp() {
        String name = "Eduardo";
        String lastName = "Barajas";
        String email = "jbarajas@gmail.com";
        String password = "password";
        Long passwordCode = 1234L;
        AppUserRole appUserRole = AppUserRole.USER;
        request1 = new RegistrationRequest(name, lastName, email, password, passwordCode);
        request2 = new RegistrationRequest(name, lastName, email, password, passwordCode);
    }

    @Test
    void testEqualsAndHashCode() {
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        String expectedToString = "RegistrationRequest(name=Eduardo, lastName=Barajas, email=jbarajas@gmail.com, password=password, passwordCode=1234)";
        assertEquals(expectedToString, request1.toString());
    }

    @Test
    void testGetters() {
        String name = "Eduardo";
        String lastName = "Barajas";
        String email = "jbarajas@gmail.com";
        String password = "password";
        Long passwordCode = 1234L;

        RegistrationRequest request = new RegistrationRequest(name, lastName, email, password, passwordCode);

        assertEquals(name, request.getName());
        assertEquals(lastName, request.getLastName());
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
        assertEquals(passwordCode, request.getPasswordCode());
    }
}