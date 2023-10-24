package com.lmsf.org.advice;

import com.lmsf.org.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errors =  new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolationException(){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", "An error has occured");
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException noSuchElementException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", noSuchElementException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorNotFoundException.class)
    public Map<String, String> handleAuthorNotFoundException(AuthorNotFoundException authorNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", authorNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenreNotFoundException.class)
    public Map<String, String> handleGenreNotFoundException(GenreNotFoundException genreNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", genreNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public Map<String, String> handleBookNotFoundException(BookNotFoundException bookNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", bookNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LibraryStaffNotFoundException.class)
    public Map<String, String> handleLibraryStaffNotFoundException(LibraryStaffNotFoundException libraryStaffNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", libraryStaffNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public Map<String, String> handleRoleNotFoundException(RoleNotFoundException roleNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", roleNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public Map<String, String> handleMemberNotFoundException(MemberNotFoundException memberNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", memberNotFoundException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenreDeleteException.class)
    public Map<String, String> handleGenreDeleteException(GenreDeleteException genreDeleteException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", genreDeleteException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintsViolationException.class)
    public Map<String, String> handleConstraintsViolationException(ConstraintsViolationException constraintsViolationException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", constraintsViolationException.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UsernameNotFoundException usernameNotFoundException){
        Map<String, String> errors =  new HashMap<>();
        errors.put("errorMessage", usernameNotFoundException.getMessage());
        return errors;
    }

}
