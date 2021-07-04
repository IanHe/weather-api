package com.service;

import com.dto.GetWeatherRequest;
import com.dto.GetWeatherResponse;

public interface ApiKeyWeatherService {
    GetWeatherResponse handleGetWeatherRequest(String apiKey, GetWeatherRequest request);

    void addApiKey(String apiKey);
}
