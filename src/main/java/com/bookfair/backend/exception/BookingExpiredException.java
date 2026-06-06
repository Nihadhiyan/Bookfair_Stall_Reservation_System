package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class BookingExpiredException extends BaseException {
    public BookingExpiredException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.GONE);
    }
    
    public BookingExpiredException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.GONE);
    }
}
