package com.repository;

import com.domain.Weather;
import com.domain.WeatherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, WeatherId> {
}
