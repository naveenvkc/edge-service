package com.polarbookshop.edgeservice.config.session;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisProperties(
    String host,
    int port
) {
}