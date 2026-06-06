package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException{
    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.FORBIDDEN);
    }
}
