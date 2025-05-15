package com.nisum.nisum.entity.user.exception;

public class EmailInvalidException extends Exception  {
    public EmailInvalidException() {
        super("email no cumple con la estructura");
    }
}
