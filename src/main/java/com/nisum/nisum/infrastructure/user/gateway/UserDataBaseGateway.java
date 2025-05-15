package com.nisum.nisum.infrastructure.user.gateway;

import com.nisum.nisum.entity.user.gateway.UserGateway;
import com.nisum.nisum.entity.user.model.User;
import com.nisum.nisum.infrastructure.db.repository.UserRepository;
import com.nisum.nisum.infrastructure.db.schema.UserSchema;
import com.nisum.nisum.infrastructure.user.dto.UserRegistrationData;

import java.util.Optional;

public class UserDataBaseGateway implements UserGateway {

    private final UserRepository userRepository;

    public UserDataBaseGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserRegistrationData user, String token) {
        return userRepository.save(new UserSchema(user, token)).toUser();
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email)
                .map(UserSchema::toUser);
    }
}
