package com.example.onlineLearning.user.registration.service;
import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;

import java.time.LocalDateTime;
import com.example.onlineLearning.user.appUser.service.AppUserService;
import com.example.onlineLearning.user.email.EmailSender;
import com.example.onlineLearning.user.registration.model.RegistrationRequest;
import com.example.onlineLearning.user.registration.token.ConfirmationToken;
import com.example.onlineLearning.user.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailSender emailSender;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Links which are connected
    @Mock
    private AppUserRepository appUserRepository;

    //Service that we are testing
    @InjectMocks
    private RegistrationService registrationService;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        //Start Mockito
        MockitoAnnotations.initMocks(this);
        appUser = new AppUser(1L, "Eduardo", "Barajas", "edubarajas98@gmail.com",
                "pa44word", 12345L, AppUserRole.USER);

        registrationService = new RegistrationService(
                appUserService, emailValidator, confirmationTokenService,
                appUserRepository, emailSender, bCryptPasswordEncoder
        );

    }

    @Test
    void testRegister() {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Eduardo", "Barajas", "email@example.com", "password", 1234L
        );

        when(emailValidator.test(registrationRequest.getEmail())).thenReturn(true);
        when(appUserService.singUpUser(any(AppUser.class))).thenReturn("token");

        String resultToken = registrationService.register(registrationRequest);
        assertNotNull(resultToken);
        assertEquals("token", resultToken);

        verify(emailSender, times(1)).send(anyString(), anyString());
    }

    @Test
    void testConfirmToken() {
        ConfirmationToken token = new ConfirmationToken();
        token.setExpiredAt(LocalDateTime.now().plusDays(1)); // Set a valid expiration time

        AppUser user = new AppUser(); // Create a mock AppUser instance
        user.setEmail("test@example.com");
        token.setAppUser(user); // Associate the AppUser with the token

        when(confirmationTokenService.getToken(anyString())).thenReturn(Optional.of(token));

        String result = registrationService.confirmToken("test_token");
        assertEquals("confirmed", result);

        verify(confirmationTokenService, times(1)).setConfirmedAt("test_token");
        verify(appUserService, times(1)).enableAppUser(anyString());
    }

    @Test
    void getAllRegisters() {
        //Start mocking this service
        //when we call appUserRepository the method is gonna return a list
        when(appUserRepository.findAll()).thenReturn(Arrays.asList(appUser));
        assertNotNull(registrationService.getAllRegisters());
    }

    @Test
    void getOnlyRegister() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.ofNullable(appUser));
        assertNotNull(registrationService.getOnlyRegister(appUser.getId()));
    }

    @Test
    void updateAppUser(){
        when(appUserRepository.findById(1L)).thenReturn(Optional.ofNullable(appUser));
        String newEmail = "newEmail@example.com";
        registrationService.updateAppUser(1L, appUser.getName(), appUser.getLastName(), newEmail,
                appUser.getPasswordCode());

        assertEquals(registrationService.updateAppUser(1L, appUser.getName(), appUser.getLastName(), newEmail,
                appUser.getPasswordCode()), registrationService.updateAppUser(1L, appUser.getName(), appUser.getLastName(), appUser.getEmail(),
                appUser.getPasswordCode()));
    }

    @Test
    void testUpdateAppUser() {
        Long id = 1L;
        String name = "Eduardo";
        String lastName = "Barajas";
        String email = "jbarajas@gmail.com";
        Long passwordCode = 1234L;

        // Create a mock AppUser
        AppUser mockAppUser = new AppUser();
        when(appUserRepository.existsById(id)).thenReturn(true);
        when(appUserRepository.findById(id)).thenReturn(java.util.Optional.of(mockAppUser));

        // Call the method
        AppUser updatedAppUser = registrationService.updateAppUser(id, name, lastName, email, passwordCode);

        // Verify changes in the mockAppUser
        assertEquals(name, updatedAppUser.getName());
        assertEquals(lastName, updatedAppUser.getLastName());
        assertEquals(email, updatedAppUser.getEmail());
        assertEquals(passwordCode, updatedAppUser.getPasswordCode());

        verify(appUserRepository, times(1)).save(updatedAppUser);
    }

    @Test
    void testUpdateAppUserNonExistent() {
        Long id = 1L;
        when(appUserRepository.existsById(id)).thenReturn(false);

        // Call the method
        AppUser result = registrationService.updateAppUser(id, "Eduardo", "Barajas", "jbarajas@gmail.com", 1234L);

        assertNull(result); // No update, so it should return null
        verify(appUserRepository, never()).save(any()); // Verify that save was not called
    }

    @Test
    void testUpdatePassword() {
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        assertNull(registrationService.updatePassword(1L, "iloveyou", "iloveyou"));
        verify(appUserRepository).existsById(Mockito.<Long>any());
    }


    @Test
    void testUpdatePasswordWrongPassword() {
        Long id = 1L;
        String password = "oldPassword";
        String newPassword = "newPassword";

        // Create a mock AppUser
        AppUser mockAppUser = new AppUser();
        mockAppUser.setPassword(bCryptPasswordEncoder.encode("differentPassword"));
        when(appUserRepository.existsById(id)).thenReturn(true);
        when(appUserRepository.findById(id)).thenReturn(java.util.Optional.of(mockAppUser));

        // Call the method and expect an exception
        assertThrows(IllegalStateException.class, () -> registrationService.updatePassword(id, password, newPassword));

        verify(appUserRepository, never()).save(any());
    }

    @Test
    void testUpdatePasswordUserNotFound() {
        Long id = 1L;
        when(appUserRepository.existsById(id)).thenReturn(false);

        // Call the method and expect no password update
        AppUser result = registrationService.updatePassword(id, "oldPassword", "newPassword");

        assertNull(result); // No update, so it should return null
        verify(appUserRepository, never()).save(any()); // Verify that save was not called
    }

    @Test
    public void testDeleteAppUser() {
        // Arrange
        Long id = 1L;
        AppUser mockUser = new AppUser();
        mockUser.setDeletedAccount(false);
        when(appUserRepository.existsById(id)).thenReturn(true);
        when(appUserRepository.findById(id)).thenReturn(Optional.of(mockUser));
        when(appUserRepository.save(Mockito.<AppUser>any())).thenReturn(mockUser);
        // Act
        AppUser result = registrationService.deleteAppUser(id);
        // Assert
        verify(appUserRepository).existsById(Mockito.<Long>any());
        verify(appUserRepository).save(Mockito.<AppUser>any());
        verify(appUserRepository).findById(Mockito.<Long>any());
        assertTrue(result.getDeletedAccount());
    }



}
