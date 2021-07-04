package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse implements Serializable {
    @JsonProperty("weather")
    private List<WeatherDTO> weathers;

    public List<WeatherDTO> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherDTO> weathers) {
        this.weathers = weathers;
    }
}
