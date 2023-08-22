package com.example.onlineLearning.user.appUser.repository;

import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class AppUserRepositoryTest {

    @MockBean
    private AppUserRepository appUserRepository;
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
    void findByEmail() {
        String email = "jbarajas@gmail.com";
        when(appUserRepository.findByEmail(email)).thenReturn(Optional.of(appUser));
        Optional<AppUser> foundUser = appUserRepository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // To disable the default transaction behavior
    public void testEnableAppUser() {
        String email = "jbarajas@gmail.com";
        int updatedRows = 1; // Simulate that one row was updated
        when(appUserRepository.enableAppUser(email)).thenReturn(updatedRows);
        int result = appUserRepository.enableAppUser(email);
        assertEquals(updatedRows, result);
    }

}