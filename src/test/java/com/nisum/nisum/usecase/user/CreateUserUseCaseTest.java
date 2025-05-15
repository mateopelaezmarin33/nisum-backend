package com.nisum.nisum.usecase.user;

import com.nisum.nisum.entity.user.exception.EmailAlreadyExistsException;
import com.nisum.nisum.entity.user.exception.EmailInvalidException;
import com.nisum.nisum.entity.user.exception.PasswordInvalidException;
import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.security.JwtUtil;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import com.nisum.nisum.infrastructure.user.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseTest {

    private UserGateway userGateway;
    private UserValidator userValidator;
    private GetUserUseCase getUserUseCase;
    private JwtUtil jwtUtil;
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userValidator = mock(UserValidator.class);
        getUserUseCase = mock(GetUserUseCase.class);
        jwtUtil = mock(JwtUtil.class);
        createUserUseCase = new CreateUserUseCase(userGateway, userValidator, getUserUseCase, jwtUtil);
    }

    @Test
    void shouldCreateUserSuccessfully() throws EmailInvalidException, PasswordInvalidException {
        UserRegistrationData data = new UserRegistrationData("Juan", "juan@test.com", "Password123", Collections.emptyList());
        User expectedUser = new User(null,"Juan", "juan@test.com", "Password123", Collections.emptyList(), "token123", null, null, null, true);

        when(userValidator.emailIsValid(data.getEmail())).thenReturn(true);
        when(userValidator.passwordIsValid(data.getPassword())).thenReturn(true);
        when(getUserUseCase.execute(data.getEmail())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(data.getEmail())).thenReturn("token123");
        when(userGateway.create(data, "token123")).thenReturn(expectedUser);

        User result = createUserUseCase.execute(data);

        assertNotNull(result);
        assertEquals("juan@test.com", result.getEmail());
        assertEquals("token123", result.getToken());
    }

    @Test
    void shouldThrowEmailInvalidException() {
        UserRegistrationData data = new UserRegistrationData("Juan", "invalid", "Password123", Collections.emptyList());
        when(userValidator.emailIsValid(data.getEmail())).thenReturn(false);

        assertThrows(EmailInvalidException.class, () -> createUserUseCase.execute(data));
    }

    @Test
    void shouldThrowPasswordInvalidException() {
        UserRegistrationData data = new UserRegistrationData("Juan", "juan@test.com", "short", Collections.emptyList());
        when(userValidator.emailIsValid(data.getEmail())).thenReturn(true);
        when(userValidator.passwordIsValid(data.getPassword())).thenReturn(false);

        assertThrows(PasswordInvalidException.class, () -> createUserUseCase.execute(data));
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        UserRegistrationData data = new UserRegistrationData("Juan", "juan@test.com", "Password123", Collections.emptyList());
        when(userValidator.emailIsValid(data.getEmail())).thenReturn(true);
        when(userValidator.passwordIsValid(data.getPassword())).thenReturn(true);
        when(getUserUseCase.execute(data.getEmail())).thenReturn(Optional.of(mock(User.class)));

        assertThrows(EmailAlreadyExistsException.class, () -> createUserUseCase.execute(data));
    }
}
