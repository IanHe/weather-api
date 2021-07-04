package com.domain;

import java.io.Serializable;
import java.util.Objects;

public class WeatherId implements Serializable {
    private String city;
    private String countryCode;

    public WeatherId() {
    }

    public WeatherId(String city, String countryCode) {
        this.city = city;
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherId weatherId = (WeatherId) o;
        return Objects.equals(city, weatherId.city) && Objects.equals(countryCode, weatherId.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, countryCode);
    }
}
