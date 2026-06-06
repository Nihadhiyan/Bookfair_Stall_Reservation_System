package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseException {
    public DuplicateResourceException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }

    public DuplicateResourceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.CONFLICT);
    }
}
