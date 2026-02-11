package com.francesco.technicaltest_backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Eccezione custom per gestire le eccezioni di business
 */
public class BusinessException extends RuntimeException {

    private final String title;
    private final HttpStatus httpStatus;

    public BusinessException(String title, HttpStatus httpStatus) {
        super(title);
        this.title = title;
        this.httpStatus = httpStatus;
    }

    public BusinessException(String title, HttpStatus httpStatus, String message) {
        super(message);
        this.title = title;
        this.httpStatus = httpStatus;
    }

    public String getTitle() {
        return title;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
