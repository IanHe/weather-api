package com.exceptions;

import com.dto.GetWeatherRequest;

public class WeatherNotFoundException extends RuntimeException {
    public WeatherNotFoundException(GetWeatherRequest request) {
        super("Unrecognized city: " + request.getCity() + ", country code: " + request.getCountryCode());
    }
}
