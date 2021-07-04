package com.service;

import com.domain.WeatherId;
import com.dto.GetWeatherRequest;

public class WeatherIdAdapter {
    public static WeatherId adapt(GetWeatherRequest req) {
        return new WeatherId(req.getCity(), req.getCountryCode());
    }
}
