package com.example.onlineLearning.user.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    void bCryptPasswordEncoder() {
        PasswordEncoder passwordEncoderConfig = new PasswordEncoder();
        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoderConfig.bCryptPasswordEncoder();

        assertNotNull(bCryptPasswordEncoder);
    }
}