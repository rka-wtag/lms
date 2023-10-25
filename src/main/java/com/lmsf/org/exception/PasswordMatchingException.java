package com.lmsf.org.exception;

public class PasswordMatchingException extends RuntimeException{
    public PasswordMatchingException(String message) {
        super(message);
    }
}
