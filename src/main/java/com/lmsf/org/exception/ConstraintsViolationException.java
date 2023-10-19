package com.lmsf.org.exception;

public class ConstraintsViolationException extends RuntimeException{
    public ConstraintsViolationException(String message) {
        super(message);
    }
}
