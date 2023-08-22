package com.example.onlineLearning.user.email;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.*;

class EmailSenderTest {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void send_Successful() {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        String to = "email@example.com";
        String content = "Hello, this is an email content.";

        emailService.send(to, content);

        verify(mailSender).send(mimeMessage);
    }
}