package com.repository;

import com.domain.Weather;
import com.domain.WeatherId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherRepositoryTest {
    @Autowired
    WeatherRepository weatherRepository;

    @Test
    public void test_crud_on_weather_repository() {
        var weather = new Weather("Melbourne", "au", "cloud", System.currentTimeMillis());
        var weatherId = new WeatherId("Melbourne", "au");
        weatherRepository.save(weather);
        Optional<Weather> getWeather = weatherRepository.findById(weatherId);
        assertThat(getWeather).isPresent();
        var newWeather = getWeather.get();
        assertThat(getWeather.get()).isEqualTo(weather);

        newWeather.setDescription("sunny");
        weatherRepository.save(newWeather);
        var updatedWeather = weatherRepository.findById(weatherId).get();
        assertThat(updatedWeather.getDescription()).isEqualTo("sunny");
        weatherRepository.deleteById(weatherId);
        assertThat(weatherRepository.count()).isEqualTo(0);
    }
}