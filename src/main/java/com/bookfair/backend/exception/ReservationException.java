package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class ReservationException extends BaseException {
    
    public ReservationException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public ReservationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.BAD_REQUEST);
    }
}
