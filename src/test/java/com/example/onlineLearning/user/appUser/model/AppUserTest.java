package com.example.onlineLearning.user.appUser.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Long passwordCode;
    private AppUserRole appUserRole;
    private Boolean locked = false; //Check whether the Account is locked
    private Boolean enabled = false; //It enables the User once they confirm their email.

    private Boolean deletedAccount = false;
    @Test
    public void testConstructor() {
        name = "Eduardo";
        lastName = "Barajas";
        email = "jbarajas@gmail.com";
        password = "password";
        passwordCode = 1234L;
        appUserRole = AppUserRole.USER;

        AppUser user = new AppUser(name, lastName, email, password, passwordCode, appUserRole);
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(passwordCode, user.getPasswordCode());
        assertEquals(appUserRole, user.getAppUserRole());
    }

    @Test
    public void testConstructorWithID() {
        id = 1L;
        name = "Eduardo";
        lastName = "Barajas";
        email = "jbarajas@gmail.com";
        password = "password";
        passwordCode = 1234L;
        appUserRole = AppUserRole.USER;

        AppUser user = new AppUser(id, name, lastName, email, password, passwordCode, appUserRole);
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(passwordCode, user.getPasswordCode());
        assertEquals(appUserRole, user.getAppUserRole());
    }

    @Test
    public void testEqualsAndHashCode() {
        AppUser user1 = new AppUser(name, lastName, email, password, passwordCode, appUserRole);
        AppUser user2 = new AppUser(name, lastName, email, password, passwordCode, appUserRole);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testUserDetailsMethods() {
        name = "Eduardo";
        lastName = "Barajas";
        email = "jbarajas@gmail.com";
        password = "password";
        passwordCode = 1234L;
        appUserRole = AppUserRole.USER;
        AppUser user = new AppUser(name, lastName, email, password, passwordCode, appUserRole);

        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(AppUserRole.USER.name()));
        assertEquals(authorities, user.getAuthorities());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertFalse(user.isEnabled());
    }

    @Test
    public void testDeletedAccount() {
        AppUser user = new AppUser(name, lastName, email, password, passwordCode, appUserRole);
        assertFalse(user.getDeletedAccount());
        user.setDeletedAccount(true);
        assertTrue(user.getDeletedAccount());
    }


}