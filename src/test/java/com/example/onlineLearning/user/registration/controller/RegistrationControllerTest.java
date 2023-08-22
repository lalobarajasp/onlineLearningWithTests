package com.example.onlineLearning.user.registration.controller;

import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import com.example.onlineLearning.user.registration.model.RegistrationRequest;
import com.example.onlineLearning.user.registration.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegistrationService registrationService;
    @Autowired
    private ObjectMapper objectMapper;
    private RegistrationRequest registrationRequest;
    private RegistrationRequest registrationRequest2;
    private AppUser appUser1;
    private AppUser appUser2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        registrationRequest = new RegistrationRequest("Eduardo", "Barajas", "email@example.com", "password", 1234L);
        registrationRequest2 = new RegistrationRequest("Eduardo", "Barajas", "email@example.com", "password", 1234L);

        Long id = 1L;
        String name = "Eduardo";
        String lastName = "Barajas";
        String email = "jbarajas@gmail.com";
        String password = "password";
        Long passwordCode = 1234L;
        AppUserRole appUserRole = AppUserRole.USER;

        appUser1 = new AppUser(id, name, lastName, email, password, passwordCode, appUserRole);
        appUser2 = new AppUser(id, name, lastName, email, password, passwordCode, appUserRole);
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testRegister() throws Exception {
        String requestBody = objectMapper.writeValueAsString(registrationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(registrationService, times(1)).register(registrationRequest);
    }

    @Test
    void testConfirm() throws Exception {
        String token = "test_token";
        String expectedResponse = "confirmed";

        when(registrationService.confirmToken(token)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/confirm")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));

        verify(registrationService, times(1)).confirmToken(token);
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetRegisters() throws Exception {
        List<AppUser> registers = new ArrayList<>();
        registers.add(appUser1);
        registers.add(appUser2);

        when(registrationService.getAllRegisters()).thenReturn(registers);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/user/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(registrationService, times(1)).getAllRegisters();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetOnlyRegister() throws Exception {
        Long pathId = 1L;
        when(registrationService.getOnlyRegister(pathId)).thenReturn(appUser1);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/user/{id}", pathId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(registrationService, times(1)).getOnlyRegister(pathId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateAppUserController() throws Exception {
        Long id = 1L;
        String updatedName = "Updated Name";
        String updatedLastName = "Updated Last Name";
        String updatedEmail = "updated@example.com";
        Long updatedPasswordCode = 5678L;

        AppUser updatedUser = new AppUser(id, updatedName, updatedLastName, updatedEmail, "password", updatedPasswordCode, AppUserRole.USER);

        when(registrationService.updateAppUser(id, updatedName, updatedLastName, updatedEmail, updatedPasswordCode)).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/user/{id}", id)
                        .param("name", updatedName)
                        .param("lastName", updatedLastName)
                        .param("email", updatedEmail)
                        .param("passwordCode", String.valueOf(updatedPasswordCode)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.lastName").value(updatedLastName))
                .andExpect(jsonPath("$.email").value(updatedEmail))
                .andExpect(jsonPath("$.passwordCode").value(updatedPasswordCode));

        verify(registrationService, times(1)).updateAppUser(id, updatedName, updatedLastName, updatedEmail, updatedPasswordCode);
    }

    @Test
    void testDeleteAppUserRegister() throws Exception {
        Long id = 1L;
        when(registrationService.deleteAppUser(id)).thenReturn(appUser1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/registration/user/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(registrationService, times(1)).deleteAppUser(id);
    }



}