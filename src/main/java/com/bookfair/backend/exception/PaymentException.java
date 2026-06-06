package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class PaymentException extends BaseException {
    public PaymentException (String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public PaymentException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.BAD_REQUEST);
    }
}
