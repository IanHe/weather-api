package com.service;

import com.exceptions.ApiKeyNotFoundException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiKeyRateLimiter {
    private Map<String, Bucket> apiKeyBucketMap;

    public ApiKeyRateLimiter() {
        this.apiKeyBucketMap = new HashMap<>();
    }

    public void add(String apiKey, int capacity, int refillRate, int refillDurationInSeconds) {
        var refill = Refill.intervally(refillRate, Duration.ofSeconds(refillDurationInSeconds));
        var limit = Bandwidth.classic(capacity, refill);
        var tokenBucket = Bucket4j.builder().addLimit(limit).build();
        this.apiKeyBucketMap.put(apiKey, tokenBucket);
    }

    public boolean tryConsume(String apiKey) {
        if (!this.apiKeyBucketMap.containsKey(apiKey))
            throw new ApiKeyNotFoundException(apiKey);
        return this.apiKeyBucketMap.get(apiKey).tryConsume(1);
    }
}
