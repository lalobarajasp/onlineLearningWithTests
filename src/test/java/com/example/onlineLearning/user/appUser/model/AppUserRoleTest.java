package com.example.onlineLearning.user.appUser.model;

import com.example.onlineLearning.enrollment.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserRoleTest {

    @Test
    void values() {
        assertEquals("USER", AppUserRole.USER.name());
        assertEquals("ADMIN", AppUserRole.ADMIN.name());
    }

    @Test
    void valueOf() {
        assertEquals(AppUserRole.USER, AppUserRole.valueOf("USER"));
        assertEquals(AppUserRole.ADMIN, AppUserRole.valueOf("ADMIN"));
    }
}