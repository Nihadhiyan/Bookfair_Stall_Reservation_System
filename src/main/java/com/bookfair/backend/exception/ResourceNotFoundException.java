package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
    
    public ResourceNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.NOT_FOUND);
    }
}
