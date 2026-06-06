package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class StallUnavailableException extends BaseException {
    public StallUnavailableException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }
    
    public StallUnavailableException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.CONFLICT);
    }
}
