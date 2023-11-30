package com.optimagrowth.organization.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "jwt.auth.converter")
@Configuration
@Data
public class JwtAuthConverterProperties {
    private String resourceId;
    private String principalAttribute;
}
