package com.example.onlineLearning.user.appUser.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordTest {

    @Test
    public void testConstructorAndGetters() {
        String password = "oldPassword";
        String newPassword = "newPassword";

        ChangePassword changePassword = new ChangePassword(password, newPassword);

        assertEquals(password, changePassword.getPassword());
        assertEquals(newPassword, changePassword.getNewPassword());
    }

    @Test
    public void testSetters() {
        ChangePassword changePassword = new ChangePassword();

        String password = "oldPassword";
        String newPassword = "newPassword";

        changePassword.setPassword(password);
        changePassword.setNewPassword(newPassword);

        assertEquals(password, changePassword.getPassword());
        assertEquals(newPassword, changePassword.getNewPassword());
    }

}