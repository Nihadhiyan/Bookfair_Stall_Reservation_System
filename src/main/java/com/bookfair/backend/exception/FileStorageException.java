package com.bookfair.backend.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends BaseException {
    public FileStorageException(String message, ErrorCode errorCode) {
        super(message, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public FileStorageException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
