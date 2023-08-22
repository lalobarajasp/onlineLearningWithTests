package com.example.onlineLearning.user.registration.token;

import com.example.onlineLearning.user.appUser.model.AppUser;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmationTokenTest {

    @Test
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        AppUser appUser = new AppUser();
        ConfirmationToken token = new ConfirmationToken("token123", now, now.plusHours(1), appUser);

        assertEquals("token123", token.getToken());
        assertEquals(now, token.getCreatedAt());
        assertEquals(now.plusHours(1), token.getExpiredAt());
        assertNull(token.getConfirmedAt());
        assertEquals(appUser, token.getAppUser());
    }

    @Test
    void testSetterAndGetters() {
        ConfirmationToken token = new ConfirmationToken();
        LocalDateTime confirmedAt = LocalDateTime.now();

        token.setToken("newToken");
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusHours(2));
        token.setConfirmedAt(confirmedAt);

        assertEquals("newToken", token.getToken());
        assertNotNull(token.getCreatedAt());
        assertNotNull(token.getExpiredAt());
        assertEquals(confirmedAt, token.getConfirmedAt());
    }

    @Test
    void testAppUserAssociation() {
        AppUser appUser = new AppUser();
        ConfirmationToken token = new ConfirmationToken();

        assertNull(token.getAppUser());

        token.setAppUser(appUser);

        assertEquals(appUser, token.getAppUser());
    }
}