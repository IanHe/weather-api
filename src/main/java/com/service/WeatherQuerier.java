package com.service;

import com.dto.GetWeatherRequest;

public interface WeatherQuerier {
    String queryWeather(GetWeatherRequest request);
}
