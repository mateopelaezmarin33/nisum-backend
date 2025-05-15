package com.nisum.nisum.entity.user.gateway;

import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;

import java.util.Optional;

public interface UserGateway {
    User create(UserRegistrationData user, String token);
    Optional<User> getUser(String email);
}
