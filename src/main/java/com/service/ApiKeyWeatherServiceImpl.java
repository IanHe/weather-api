package com.service;

import com.dto.GetWeatherRequest;
import com.dto.GetWeatherResponse;
import com.exceptions.ApiKeyNotFoundException;
import com.exceptions.WeatherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("apiKeyWeatherService")
public class ApiKeyWeatherServiceImpl implements ApiKeyWeatherService {
    private final int API_KEY_RATE_LIMIT_CAPACITY = 5;
    private final int API_KEY_RATE_LIMIT_REFILL_RATE = 5;
    private final int API_KEY_RATE_LIMIT_REFILL_DURATION_IN_SECONDS = 3600;

    private ApiKeyRateLimiter apiKeyRateLimiter;

    private WeatherQuerier weatherQuerier;

    @Autowired
    public ApiKeyWeatherServiceImpl(ApiKeyRateLimiter apiKeyRateLimiter, WeatherQuerier weatherQuerier) {
        this.apiKeyRateLimiter = apiKeyRateLimiter;
        this.weatherQuerier = weatherQuerier;
    }

    public GetWeatherResponse handleGetWeatherRequest(String apiKey, GetWeatherRequest request) {
        try {
            var consumed = this.apiKeyRateLimiter.tryConsume(apiKey);
            if (!consumed) {
                return new GetWeatherResponse(HttpStatus.TOO_MANY_REQUESTS, null, null);
            }
            var description = this.weatherQuerier.queryWeather(request);
            return new GetWeatherResponse(HttpStatus.OK, null, description);
        } catch (ApiKeyNotFoundException | WeatherNotFoundException ex) {
            return new GetWeatherResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        } catch (Exception ex) {
            var msg = "unpredicted exception happened";
            return new GetWeatherResponse(HttpStatus.BAD_REQUEST, msg, null);
        }
    }

    public void addApiKey(String apiKey) {
        this.apiKeyRateLimiter.add(
                apiKey,
                API_KEY_RATE_LIMIT_CAPACITY,
                API_KEY_RATE_LIMIT_REFILL_RATE,
                API_KEY_RATE_LIMIT_REFILL_DURATION_IN_SECONDS);
    }
}
