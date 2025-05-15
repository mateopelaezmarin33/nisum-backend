package com.nisum.nisum.usecase.user;

import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.entity.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetUserUseCaseTest {

    private UserGateway userGateway;
    private GetUserUseCase getUserUseCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        getUserUseCase = new GetUserUseCase(userGateway);
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        String email = "test@example.com";
        User mockUser = new User(null,"Juan", email, "password123", null, "token123", null, null, null, true);

        when(userGateway.getUser(email)).thenReturn(Optional.of(mockUser));

        Optional<User> result = getUserUseCase.execute(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        String email = "notfound@example.com";

        when(userGateway.getUser(email)).thenReturn(Optional.empty());

        Optional<User> result = getUserUseCase.execute(email);

        assertFalse(result.isPresent());
    }
}
