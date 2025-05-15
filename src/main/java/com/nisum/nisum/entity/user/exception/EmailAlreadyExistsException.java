package com.nisum.nisum.entity.user.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Usuario ya registrado con email: " + email);
    }
}
