package com.nisum.nisum.usecase.user;


import com.nisum.nisum.entity.user.exception.EmailAlreadyExistsException;
import com.nisum.nisum.entity.user.exception.EmailInvalidException;
import com.nisum.nisum.entity.user.exception.PasswordInvalidException;
import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.security.JwtUtil;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;
import com.nisum.nisum.infrastructure.user.validation.UserValidator;


public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserValidator userValidator;
    private final GetUserUseCase getUserUseCase;
    private final JwtUtil jwtUtil;
    public CreateUserUseCase(UserGateway userGateway, UserValidator userValidator, GetUserUseCase getUserUseCase, JwtUtil jwtUtil) {
        this.userGateway = userGateway;
        this.userValidator = userValidator;
        this.getUserUseCase = getUserUseCase;
        this.jwtUtil = jwtUtil;
    }

    public User execute(UserRegistrationData data) throws PasswordInvalidException, EmailInvalidException, EmailAlreadyExistsException {
        if (!userValidator.emailIsValid(data.getEmail())) {
            throw new EmailInvalidException();
        }
        if (!userValidator.passwordIsValid(data.getPassword())) {
            throw new PasswordInvalidException();
        }
        if (getUserUseCase.execute(data.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(data.getEmail());
        }
        String token = jwtUtil.generateToken(data.getEmail());

        return this.userGateway.create(data, token);
    }
}
