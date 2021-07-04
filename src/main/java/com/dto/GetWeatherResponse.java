package com.dto;

import org.springframework.http.HttpStatus;

public class GetWeatherResponse {
    private HttpStatus status;
    private String message;
    private String description;

    public GetWeatherResponse() {
    }

    public GetWeatherResponse(HttpStatus status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
