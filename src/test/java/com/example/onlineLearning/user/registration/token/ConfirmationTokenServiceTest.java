package com.example.onlineLearning.user.registration.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfirmationTokenServiceTest {
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    private ConfirmationToken token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Create a sample token
        token = new ConfirmationToken("token123", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null);
    }

    @Test
    void getToken() {
        when(confirmationTokenRepository.findByToken("token123")).thenReturn(Optional.of(token));

        Optional<ConfirmationToken> result = confirmationTokenService.getToken("token123");

        assertTrue(result.isPresent());
        assertEquals(token, result.get());

        verify(confirmationTokenRepository, times(1)).findByToken("token123");
    }

    @Test
    void saveConfirmationToken() {
        confirmationTokenService.saveConfirmationToken(token);
        verify(confirmationTokenRepository, times(1)).save(token);
    }

    @Test
    void setConfirmedAt() {
        when(confirmationTokenRepository.updateConfirmedAt(eq("token123"), any(LocalDateTime.class))).thenReturn(1);

        int result = confirmationTokenService.setConfirmedAt("token123");

        assertEquals(1, result);

        verify(confirmationTokenRepository, times(1)).updateConfirmedAt(eq("token123"), any(LocalDateTime.class));
    }
}