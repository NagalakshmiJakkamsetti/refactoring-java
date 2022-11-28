package com.etraveli.refactoring.Exception;

public class NoMovieFoundException extends RuntimeException {
    public NoMovieFoundException() {
        super();
    }

    public NoMovieFoundException(String message) {
        super(message);
    }
}

