package com.service;

import com.domain.WeatherId;
import com.dto.GetWeatherRequest;
import com.exceptions.WeatherNotFoundException;
import com.repository.WeatherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherQuerierImplIntegrationTest {

    @Autowired
    WeatherQuerier weatherQuerier;

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    public void test_query_weather_it_should_query_from_open_weather_and_save_data() {
        var req = new GetWeatherRequest("Melbourne", "au");
        var description = weatherQuerier.queryWeather(req);
        var foundWeather = weatherRepository.findById(new WeatherId("Melbourne", "au"));
        assertThat(foundWeather).isPresent();
        assertThat(foundWeather.get().getDescription()).isEqualTo(description);
    }

    @Test
    public void test_query_weather_it_should_throw_weather_not_found_exception_given_unkown_city() {
        var req = new GetWeatherRequest("Unknown", "au");
        WeatherNotFoundException ex = assertThrows(WeatherNotFoundException.class, () -> {
            weatherQuerier.queryWeather(req);
        });
        assertThat(ex.getMessage()).isEqualTo("Unrecognized city: Unknown, country code: au");
    }
}