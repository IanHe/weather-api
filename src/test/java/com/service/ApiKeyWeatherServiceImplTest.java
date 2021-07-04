package com.service;

import com.dto.GetWeatherRequest;
import com.exceptions.ApiKeyNotFoundException;
import com.exceptions.WeatherNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiKeyWeatherServiceImplTest {

    @MockBean
    ApiKeyRateLimiter apiKeyRateLimiter;

    @MockBean
    WeatherQuerier weatherQuerier;

    @Test
    public void test_handle_get_weather_request_when_handle_unknown_api_key() {
        var apiKey = "unknownApiKey";
        ApiKeyWeatherServiceImpl apiKeyWeatherService = new ApiKeyWeatherServiceImpl(apiKeyRateLimiter, weatherQuerier);
        Mockito.when(apiKeyRateLimiter.tryConsume(apiKey)).thenThrow(new ApiKeyNotFoundException(apiKey));
        var request = Mockito.mock(GetWeatherRequest.class);
        var resp = apiKeyWeatherService.handleGetWeatherRequest(apiKey, request);
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resp.getDescription()).isNull();
        assertThat(resp.getMessage()).isEqualTo("unknownApiKey is not found");
    }

    @Test
    public void test_handle_get_weather_request_when_rate_limit_is_exceeded() {
        var apiKey = "someApiKey";
        ApiKeyWeatherServiceImpl apiKeyWeatherService = new ApiKeyWeatherServiceImpl(apiKeyRateLimiter, weatherQuerier);
        Mockito.when(apiKeyRateLimiter.tryConsume(apiKey)).thenReturn(false);
        var request = Mockito.mock(GetWeatherRequest.class);
        var resp = apiKeyWeatherService.handleGetWeatherRequest(apiKey, request);
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
        assertThat(resp.getDescription()).isNull();
        assertThat(resp.getMessage()).isNull();
    }

    @Test
    public void test_handle_get_weather_request_when_city_is_not_found() {
        var apiKey = "someApiKey";
        ApiKeyWeatherServiceImpl apiKeyWeatherService = new ApiKeyWeatherServiceImpl(apiKeyRateLimiter, weatherQuerier);
        var request = new GetWeatherRequest("unknownCity", "au");
        Mockito.when(apiKeyRateLimiter.tryConsume(apiKey)).thenReturn(true);
        Mockito.when(weatherQuerier.queryWeather(request)).thenThrow(new WeatherNotFoundException(request));
        var resp = apiKeyWeatherService.handleGetWeatherRequest(apiKey, request);
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resp.getDescription()).isNull();
        assertThat(resp.getMessage()).isEqualTo("Unrecognized city: unknownCity, country code: au");
    }

    @Test
    public void test_handle_get_weather_request_when_city_is_found() {
        var apiKey = "someApiKey";
        ApiKeyWeatherServiceImpl apiKeyWeatherService = new ApiKeyWeatherServiceImpl(apiKeyRateLimiter, weatherQuerier);
        var request = Mockito.mock(GetWeatherRequest.class);
        Mockito.when(apiKeyRateLimiter.tryConsume(apiKey)).thenReturn(true);
        Mockito.when(weatherQuerier.queryWeather(request)).thenReturn("Sunny");
        var resp = apiKeyWeatherService.handleGetWeatherRequest(apiKey, request);
        assertThat(resp.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getDescription()).isEqualTo("Sunny");
        assertThat(resp.getMessage()).isNull();
    }
}