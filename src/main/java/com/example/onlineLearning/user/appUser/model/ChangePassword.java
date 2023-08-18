package com.example.onlineLearning.user.appUser.model;

public class ChangePassword extends AppUser {
    private String password;
    private String newPassword;

    public ChangePassword(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public ChangePassword() {
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
