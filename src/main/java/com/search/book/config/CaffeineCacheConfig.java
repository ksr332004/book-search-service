package com.search.book.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class CaffeineCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(c -> new CaffeineCache(c.getCacheName(), Caffeine.newBuilder()
                                .initialCapacity(c.getInitialCapacity())
                                .expireAfterWrite(c.getExpiredAfterWrite(), TimeUnit.SECONDS)
                                .maximumSize(c.getMaximumSize())
                                .softValues()
                                .recordStats()
                                .build()
                        )
                )
                .collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}