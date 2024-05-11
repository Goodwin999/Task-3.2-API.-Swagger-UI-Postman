package ru.hogwarts.school.exception;



public class DatabaseAccessException extends RuntimeException  {

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
