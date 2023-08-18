package com.example.onlineLearning.user.registration.service;
import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistrationServiceTest {

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

    }

    @Test
    void getAllRegisters() {
        //Start mocking this service
        //when we call appUserRepository the method is gonna return a list
        when(appUserRepository.findAll()).thenReturn(Arrays.asList(appUser));
        assertNotNull(registrationService.getAllRegisters());
    }

    /**
     * Method under test: {@link RegistrationService#getAllRegisters()}
     */
    @Test
    void testGetAllRegisters() {
        ArrayList<AppUser> appUserList = new ArrayList<>();
        when(appUserRepository.findAll()).thenReturn(appUserList);
        List<AppUser> actualAllRegisters = registrationService.getAllRegisters();
        assertSame(appUserList, actualAllRegisters);
        assertTrue(actualAllRegisters.isEmpty());
        verify(appUserRepository).findAll();
    }

    /**
     * Method under test: {@link RegistrationService#getAllRegisters()}
     */
    @Test
    void testGetAllRegisters2() {
        when(appUserRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> registrationService.getAllRegisters());
        verify(appUserRepository).findAll();
    }

    @Test
    void getOnlyRegister() {
        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.ofNullable(appUser));
        assertNotNull(registrationService.getOnlyRegister(appUser.getId()));
    }

    /**
     * Method under test: {@link RegistrationService#getOnlyRegister(Long)}
     */
    @Test
    void testGetOnlyRegister() {
        AppUser appUser = new AppUser();
        appUser.setAppUserRole(AppUserRole.USER);
        appUser.setDeletedAccount(true);
        appUser.setEmail("jane.doe@example.org");
        appUser.setEnabled(true);
        appUser.setId(1L);
        appUser.setLastName("Doe");
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setPasswordCode(1L);
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(appUser, registrationService.getOnlyRegister(1L));
        verify(appUserRepository).findById(Mockito.<Long>any());
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

    /**
     * Method under test: {@link RegistrationService#updatePassword(Long, String, String)}
     */
    @Test
    void testUpdatePassword4() {
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        assertNull(registrationService.updatePassword(1L, "iloveyou", "iloveyou"));
        verify(appUserRepository).existsById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RegistrationService#deleteAppUser(Long)}
     */
    @Test
    void testDeleteAppUser() {
        AppUser appUser = new AppUser();
        appUser.setAppUserRole(AppUserRole.USER);
        appUser.setDeletedAccount(true);
        appUser.setEmail("jane.doe@example.org");
        appUser.setEnabled(true);
        appUser.setId(1L);
        appUser.setLastName("Doe");
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setPasswordCode(1L);
        Optional<AppUser> ofResult = Optional.of(appUser);

        AppUser appUser2 = new AppUser();
        appUser2.setAppUserRole(AppUserRole.USER);
        appUser2.setDeletedAccount(true);
        appUser2.setEmail("jane.doe@example.org");
        appUser2.setEnabled(true);
        appUser2.setId(1L);
        appUser2.setLastName("Doe");
        appUser2.setLocked(true);
        appUser2.setName("Name");
        appUser2.setPassword("iloveyou");
        appUser2.setPasswordCode(1L);
        when(appUserRepository.save(Mockito.<AppUser>any())).thenReturn(appUser2);
        when(appUserRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(appUserRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        assertSame(appUser, registrationService.deleteAppUser(1L));
        verify(appUserRepository).existsById(Mockito.<Long>any());
        verify(appUserRepository).save(Mockito.<AppUser>any());
        verify(appUserRepository).findById(Mockito.<Long>any());
    }

    @Test
    public void testDeleteAppUser2() {
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
