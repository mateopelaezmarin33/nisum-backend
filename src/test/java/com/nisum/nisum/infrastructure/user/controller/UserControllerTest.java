package com.nisum.nisum.infrastructure.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.nisum.entity.user.exception.EmailAlreadyExistsException;
import com.nisum.nisum.entity.user.exception.EmailInvalidException;
import com.nisum.nisum.entity.user.exception.PasswordInvalidException;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import com.nisum.nisum.usecase.user.CreateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    private UserRegistrationData validUserData;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CreateUserUseCase createUserUseCase() {
            return mock(CreateUserUseCase.class);
        }
    }

    @BeforeEach
    void setUp() {
        validUserData = new UserRegistrationData("Juan", "juan@test.com", "Password1!", Collections.emptyList());
    }


    @Test
    void shouldReturnBadRequestWhenEmailAlreadyExistsException() throws Exception {
        when(createUserUseCase.execute(any())).thenThrow(new EmailAlreadyExistsException("emao@hotmail.com"));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Usuario ya registrado con email: emao@hotmail.com"));
    }
}
