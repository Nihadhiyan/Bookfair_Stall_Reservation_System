package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException  {
    public UnauthorizedException (String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.UNAUTHORIZED);
    }
}
