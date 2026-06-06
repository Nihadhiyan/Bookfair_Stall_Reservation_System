package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    public BusinessException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }

    public BusinessException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.CONFLICT);
    }
}
