package com.example.onlineLearning.user.registration.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String name;
    private final String lastName;
    private final String email;
    private final String password;
    private final Long passwordCode;
}
