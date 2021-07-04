package com.service;

import com.domain.Weather;
import com.dto.GetWeatherRequest;
import com.exceptions.WeatherNotFoundException;
import com.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherQuerierImpl implements WeatherQuerier {
    private final int WEATHER_EXPIRE_TIME_IN_MILLIS = 3600 * 1000;

    private WeatherRepository weatherRepository;

    private OpenWeatherClient openWeatherClient;

    @Autowired
    public WeatherQuerierImpl(WeatherRepository weatherRepository, OpenWeatherClient openWeatherClient) {
        this.weatherRepository = weatherRepository;
        this.openWeatherClient = openWeatherClient;
    }

    @Override
    public String queryWeather(GetWeatherRequest request) {
        var foundWeather = weatherRepository.findById(WeatherIdAdapter.adapt(request));
        return foundWeather.map(weather -> {
            if (!weather.expired()) {
                return weather.getDescription();
            }
            return this.queryOpenWeatherAndSave(request);
        }).orElseGet(() -> this.queryOpenWeatherAndSave(request));
    }

    private String queryOpenWeatherAndSave(GetWeatherRequest request) {
        var description = this.queryOpenWeather(request);
        var newWeather = new Weather(request.getCity(), request.getCountryCode(), description, System.currentTimeMillis() + WEATHER_EXPIRE_TIME_IN_MILLIS);
        weatherRepository.saveAndFlush(newWeather);
        return description;
    }

    private String queryOpenWeather(GetWeatherRequest request) {
        var response = this.openWeatherClient.get(request);
        var weathers = response.getWeathers();
        if (weathers == null || weathers.isEmpty()) {
            throw new WeatherNotFoundException(request);
        }
        return weathers.get(0).getDescription();
    }
}
