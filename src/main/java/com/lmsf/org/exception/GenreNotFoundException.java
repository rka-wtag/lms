package com.lmsf.org.exception;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String message) {
        super(message);
    }
}
