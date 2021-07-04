package com.service;

import com.dto.GetWeatherRequest;
import com.dto.OpenWeatherResponse;
import com.exceptions.WeatherNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherClient {
    Logger logger = LoggerFactory.getLogger(OpenWeatherClient.class);

    private String server = "https://api.openweathermap.org";
    private String apiKey;
    private RestTemplate rest;

    @Autowired
    public OpenWeatherClient(Environment env) {
        this.rest = new RestTemplate();
        this.apiKey = env.getProperty("openweather.apikey");
    }

    public OpenWeatherResponse get(GetWeatherRequest request) {
        var url = server + request.openWeatherUri(apiKey);
        try {
            return rest.getForObject(url, OpenWeatherResponse.class);
        } catch (RestClientException ex) {
            logger.error("get GetWeatherRequest with exception" + request);
            logger.error(ex.getMessage(), ex);
            throw new WeatherNotFoundException(request);
        }
    }

}
