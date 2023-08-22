package com.example.onlineLearning.user.appUser.service;

import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;
import com.example.onlineLearning.user.registration.token.ConfirmationToken;
import com.example.onlineLearning.user.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private AppUserService appUserService;
    private AppUser appUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Long id = 1L;
        String name = "Eduardo";
        String lastName = "Barajas";
        String email = "jbarajas@gmail.com";
        String password = "password";
        Long passwordCode = 1234L;
        AppUserRole appUserRole = AppUserRole.USER;

        appUser = new AppUser(id, name, lastName, email, password, passwordCode, appUserRole);
    }

    @Test
    void loadUserByUsername() {
        String email = "jbarajas@gmail.com";
        when(appUserRepository.findByEmail(email)).thenReturn(Optional.of(appUser));

        UserDetails userDetails = appUserService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotExists_ThrowsException() {
        String email = "nonexistent@example.com";
        when(appUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> appUserService.loadUserByUsername(email));
    }

    @Test
    void singUpUser() {
        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(appUser.getPassword())).thenReturn("encodedPassword");

        ArgumentCaptor<ConfirmationToken> tokenCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
        doNothing().when(confirmationTokenService).saveConfirmationToken(tokenCaptor.capture());

        String token = appUserService.singUpUser(appUser);

        assertNotNull(token);
        verify(appUserRepository).save(appUser);
        verify(confirmationTokenService).saveConfirmationToken(any(ConfirmationToken.class));

        ConfirmationToken capturedToken = tokenCaptor.getValue();
        assertNotNull(capturedToken);
        assertEquals(appUser, capturedToken.getAppUser());

    }

    @Test
    void signUpUser_UserExists_ThrowsException() {
        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));
        assertThrows(IllegalStateException.class, () -> appUserService.singUpUser(appUser));
    }

    @Test
    void enableAppUser() {
        String email = "jbarajas@gmail.com";
        int updatedRows = 1;

        when(appUserRepository.enableAppUser(email)).thenReturn(updatedRows);

        int result = appUserService.enableAppUser(email);

        assertEquals(updatedRows, result);
        verify(appUserRepository).enableAppUser(email);
    }
}