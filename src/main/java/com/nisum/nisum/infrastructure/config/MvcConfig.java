package com.nisum.nisum.infrastructure.config;

import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.infrastructure.db.repository.UserRepository;
import com.nisum.nisum.infrastructure.security.JwtUtil;
import com.nisum.nisum.infrastructure.user.gateway.UserDataBaseGateway;
import com.nisum.nisum.infrastructure.user.validation.UserValidator;
import com.nisum.nisum.usecase.user.CreateUserUseCase;
import com.nisum.nisum.usecase.user.GetUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository, UserValidator userValidator, GetUserUseCase getUserUseCase,
            JwtUtil jwtUtil) {
        UserGateway userGateway = new UserDataBaseGateway(userRepository);
        return new CreateUserUseCase(userGateway, userValidator, getUserUseCase, jwtUtil);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepository userRepository) {
        UserGateway userGateway = new UserDataBaseGateway(userRepository);
        return new GetUserUseCase(userGateway);
    }
}
