package com.service;

import com.exceptions.ApiKeyNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiKeyRateLimiterTest {
    ApiKeyRateLimiter apiKeyRateLimiter = new ApiKeyRateLimiter();

    @Test
    public void test_consume_token_should_be_refilled_after_two_seconds() throws InterruptedException {
        var apiKey = "someApiKey";
        apiKeyRateLimiter.add(apiKey, 10, 10, 2);
        for (int i = 0; i < 10; i++) {
            assertTrue(apiKeyRateLimiter.tryConsume(apiKey));
        }
        assertFalse(apiKeyRateLimiter.tryConsume(apiKey));
        Thread.sleep(2000);
        assertTrue(apiKeyRateLimiter.tryConsume(apiKey));
    }

    @Test
    public void test_consume_token_should_throw_api_key_not_found_exception() {
        apiKeyRateLimiter.add("someKey", 10, 10, 2);
        ApiKeyNotFoundException ex = assertThrows(ApiKeyNotFoundException.class, () -> {
            apiKeyRateLimiter.tryConsume("unknownKey");
        });
        assertThat(ex.getMessage()).isEqualTo("unknownKey is not found");
    }
}