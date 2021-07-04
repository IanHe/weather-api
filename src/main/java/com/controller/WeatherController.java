package com.controller;

import com.dto.GetWeatherRequest;
import com.service.HttpResponseAdapter;
import com.service.ApiKeyWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private ApiKeyWeatherService apiKeyWeatherService;

    @Autowired
    public WeatherController(ApiKeyWeatherService apiKeyWeatherService) {
        this.apiKeyWeatherService = apiKeyWeatherService;
    }

    // Find
    @GetMapping("/weather")
    ResponseEntity<String> find(@RequestHeader("api-key") String apiKey, @RequestParam String city, @RequestParam String country) {
        var req = new GetWeatherRequest(city, country);
        var resp = this.apiKeyWeatherService.handleGetWeatherRequest(apiKey, req);
        return HttpResponseAdapter.adapt(resp);
    }
}
