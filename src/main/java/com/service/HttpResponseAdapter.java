package com.service;

import com.dto.GetWeatherResponse;
import org.springframework.http.ResponseEntity;

public class HttpResponseAdapter {
    public static ResponseEntity<String> adapt(GetWeatherResponse resp) {
        var msg = resp.getStatus().is2xxSuccessful() ? resp.getDescription() : resp.getMessage();
        return new ResponseEntity<>(msg, resp.getStatus());
    }
}
