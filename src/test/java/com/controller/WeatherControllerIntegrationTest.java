package com.controller;

import com.service.ApiKeyWeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WeatherControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiKeyWeatherService apiKeyWeatherService;

    @Test
    public void test_get_weather_200_return_weather_description() throws Exception {
        var apiKey = "someApiKey";
        apiKeyWeatherService.addApiKey(apiKey);
        mockMvc.perform(get("/weather?city=Melbourne&country=au")
                .header("api-key", apiKey))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void test_get_weather_400_when_api_key_is_unfound() throws Exception {
        var apiKey = "unknownApiKey";
        mockMvc.perform(get("/weather?city=Melbourne&country=au")
                .header("api-key", apiKey))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("unknownApiKey is not found"));
    }

    @Test
    public void test_get_weather_400_when_city_is_unfound() throws Exception {
        var apiKey = "someApiKey";
        apiKeyWeatherService.addApiKey(apiKey);
        mockMvc.perform(get("/weather?city=Unknown&country=au")
                .header("api-key", apiKey))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unrecognized city: Unknown, country code: au"));
    }

    @Test
    public void test_get_weather_429_when_calling_api_too_many_times() throws Exception {
        var apiKey = "someApiKey";
        apiKeyWeatherService.addApiKey(apiKey);
        mockMvc.perform(get("/weather?city=Melbourne&country=au").header("api-key", apiKey));
        mockMvc.perform(get("/weather?city=Melbourne&country=au").header("api-key", apiKey));
        mockMvc.perform(get("/weather?city=Melbourne&country=au").header("api-key", apiKey));
        mockMvc.perform(get("/weather?city=Melbourne&country=au").header("api-key", apiKey));
        mockMvc.perform(get("/weather?city=Melbourne&country=au").header("api-key", apiKey));
        mockMvc.perform(get("/weather?city=Melbourne&country=au")
                .header("api-key", apiKey))
                .andDo(print())
                .andExpect(status().isTooManyRequests());
    }
}