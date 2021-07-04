package com.exceptions;

public class ApiKeyNotFoundException extends RuntimeException {
    public ApiKeyNotFoundException(String apiKey) {
        super(apiKey + " is not found");
    }
}
