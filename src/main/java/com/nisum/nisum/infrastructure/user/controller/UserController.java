package com.nisum.nisum.infrastructure.user.controller;

import com.nisum.nisum.entity.user.exception.EmailAlreadyExistsException;
import com.nisum.nisum.entity.user.exception.EmailInvalidException;
import com.nisum.nisum.entity.user.exception.PasswordInvalidException;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import com.nisum.nisum.usecase.user.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserRegistrationData userData) {
        try {
            User createdUser = createUserUseCase.execute(userData);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (EmailInvalidException | PasswordInvalidException | EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage()));
        }
    }
}
