package com.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(WeatherId.class)
public class Weather {
    @Id
    private String city;
    @Id
    private String countryCode;
    @NotEmpty(message = "weather description cannot be empty")
    private String description;
    @NotNull(message = "expireTimestamp cannot be null")
    private Long expireTimestamp;

    public Weather() {
    }

    public Weather(String city, String countryCode, String description, Long expireTimestamp) {
        this.city = city;
        this.countryCode = countryCode;
        this.description = description;
        this.expireTimestamp = expireTimestamp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(Long expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    public boolean expired() {
        return this.expireTimestamp >= System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(city, weather.city) && Objects.equals(countryCode, weather.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, countryCode);
    }
}
