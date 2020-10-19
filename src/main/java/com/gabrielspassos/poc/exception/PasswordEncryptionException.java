package com.gabrielspassos.poc.exception;

import org.springframework.http.HttpStatus;

public class PasswordEncryptionException extends BusinessException {

    public PasswordEncryptionException(String code, String message) {
        super(code, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
