package com.nisum.nisum.usecase.user;

import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.entity.user.model.User;

import java.util.Optional;

public class GetUserUseCase {
    private final UserGateway userGateway;

    public GetUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Optional<User> execute(String email) {
        return this.userGateway.getUser(email);
    }
}
