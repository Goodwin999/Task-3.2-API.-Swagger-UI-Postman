package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException() {
        super();
    }

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseAccessException(Throwable cause) {
        super(cause);
    }

    @ExceptionHandler(DatabaseAccessException.class)
    public ResponseEntity<String> handleDatabaseAccessException(DatabaseAccessException ex) {
        String errorMessage = "Произошла ошибка доступа к базе данных: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

