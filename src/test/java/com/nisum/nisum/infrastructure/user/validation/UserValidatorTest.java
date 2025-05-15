package com.nisum.nisum.infrastructure.user.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        ReflectionTestUtils.setField(userValidator, "emailRegex", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        ReflectionTestUtils.setField(userValidator, "passwordRegex", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    @Test
    void shouldValidateCorrectEmail() {
        assertTrue(userValidator.emailIsValid("user@example.com"));
    }

    @Test
    void shouldInvalidateIncorrectEmail() {
        assertFalse(userValidator.emailIsValid("invalid-email"));
        assertFalse(userValidator.emailIsValid(""));
        assertFalse(userValidator.emailIsValid(null));
    }

    @Test
    void shouldValidateCorrectPassword() {
        assertTrue(userValidator.passwordIsValid("Password1!"));
    }

    @Test
    void shouldInvalidateIncorrectPassword() {
        assertFalse(userValidator.passwordIsValid("pass")); // too short
        assertFalse(userValidator.passwordIsValid("password")); // no upper, number, symbol
        assertFalse(userValidator.passwordIsValid(null));
        assertFalse(userValidator.passwordIsValid("   ")); // blank
    }
}