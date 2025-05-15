package com.nisum.nisum.infrastructure.user.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    @Value("${email.regex}")
    private String emailRegex;

    @Value("${password.regex}")
    private String passwordRegex;

    public boolean passwordIsValid(String password) {
        return password != null && !password.isBlank() && password.matches(passwordRegex);
    }

    public boolean emailIsValid(String email) {
        return email != null && !email.isBlank() && email.matches(emailRegex);
    }
}
