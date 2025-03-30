package com.zchadli.xoz_backend.exception;

public class CurrentGameNotFoundException extends RuntimeException {
    public CurrentGameNotFoundException(String message) {
        super(message);
    }
}
