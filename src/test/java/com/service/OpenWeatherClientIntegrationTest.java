package com.service;

import com.dto.GetWeatherRequest;
import com.dto.OpenWeatherResponse;
import com.exceptions.WeatherNotFoundException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherClientIntegrationTest {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Test
    public void get_weather_from_open_weather() {
        var request = new GetWeatherRequest("Melbourne", "au");
        var requestForUnknownCity = new GetWeatherRequest("Some", "au");
        var resp = openWeatherClient.get(request);
        assertThat(resp).isInstanceOf(OpenWeatherResponse.class);
        WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class, () -> {
            openWeatherClient.get(requestForUnknownCity);
        });
        assertThat(exception.getMessage()).isEqualTo("Unrecognized city: Some, country code: au");
    }
}