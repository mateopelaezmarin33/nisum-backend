package com.nisum.nisum.entity.user.exception;

public class PasswordInvalidException extends Exception  {
    public PasswordInvalidException() {
        super("Contraseña no cumple con los parametros");
    }
}
