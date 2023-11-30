package com.optimagrowth.license.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
@Data
public class JwtAuthConverterProperties {
    private String resourceId;
    private String principalAttribute;
}
