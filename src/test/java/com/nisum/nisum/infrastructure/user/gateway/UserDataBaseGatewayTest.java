package com.nisum.nisum.infrastructure.user.gateway;

import com.nisum.nisum.entity.user.model.Phone;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.db.repository.UserRepository;
import com.nisum.nisum.infrastructure.db.schema.UserSchema;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserDataBaseGatewayTest {

    private UserRepository userRepository;
    private UserDataBaseGateway gateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        gateway = new UserDataBaseGateway(userRepository);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserRegistrationData data = new UserRegistrationData("Ana", "ana@example.com", "Secret123$", Collections.emptyList());
        User expectedUser = User.builder()
                .name("Ana")
                .email("ana@example.com")
                .password("Secret123$")
                .phones(Collections.emptyList())
                .token("token123")
                .isActive(true)
                .build();

        UserSchema mockSchema = mock(UserSchema.class);
        when(mockSchema.toUser()).thenReturn(expectedUser);
        when(userRepository.save(any(UserSchema.class))).thenReturn(mockSchema);

        User result = gateway.create(data, "token123");

        assertNotNull(result);
        assertEquals("ana@example.com", result.getEmail());
        assertEquals("token123", result.getToken());
    }

    @Test
    void shouldReturnUserWhenEmailExists() {
        UserSchema schema = mock(UserSchema.class);
        User user = User.builder()
                .name("Luis")
                .email("luis@example.com")
                .password("123456Aa!")
                .phones(Collections.singletonList(new Phone("123456", "1", "57")))
                .token("secure-token")
                .isActive(true)
                .build();

        when(schema.toUser()).thenReturn(user);
        when(userRepository.findByEmail("luis@example.com")).thenReturn(Optional.of(schema));

        Optional<User> result = gateway.getUser("luis@example.com");

        assertTrue(result.isPresent());
        assertEquals("luis@example.com", result.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Optional<User> result = gateway.getUser("notfound@example.com");

        assertTrue(result.isEmpty());
    }
}
