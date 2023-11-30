package com.optimagrowth.license.caching.Component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {
    private String property;
    @Value("${redis.server}")
    private String redisServer="";
    @Value("${redis.port}")
    private String redisPort="";
}
