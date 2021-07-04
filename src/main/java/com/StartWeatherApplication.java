package com;

import com.service.ApiKeyWeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class StartWeatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartWeatherApplication.class, args);
    }

    // run this only on profile 'demo', avoid run this in test
    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(ApiKeyWeatherService apiKeyWeatherService) {
        return args -> {
            apiKeyWeatherService.addApiKey("apikey-domo-1");
            apiKeyWeatherService.addApiKey("apikey-domo-2");
            apiKeyWeatherService.addApiKey("apikey-domo-3");
            apiKeyWeatherService.addApiKey("apikey-domo-4");
            apiKeyWeatherService.addApiKey("apikey-domo-5");
        };
    }
}
