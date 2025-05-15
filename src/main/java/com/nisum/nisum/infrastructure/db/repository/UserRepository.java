package com.nisum.nisum.infrastructure.db.repository;

import com.nisum.nisum.infrastructure.db.schema.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSchema, Long> {
    Optional<UserSchema> findByEmail(String email);
}
