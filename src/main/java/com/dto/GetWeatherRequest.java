package com.dto;

public class GetWeatherRequest {
    private String city;
    private String countryCode;

    public GetWeatherRequest(String city, String countryCode) {
        this.city = city;
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String openWeatherUri(String apiKey) {
        return String.format("/data/2.5/weather?q=%s,%s&appid=%s", city, countryCode, apiKey);
    }

    @Override
    public String toString() {
        return "GetWeatherRequest: " + city + ", " + countryCode;
    }
}
