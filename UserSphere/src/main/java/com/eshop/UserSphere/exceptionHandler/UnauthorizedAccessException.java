package com.eshop.UserSphere.exceptionHandler;

public class UnauthorizedAccessException extends Throwable {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
