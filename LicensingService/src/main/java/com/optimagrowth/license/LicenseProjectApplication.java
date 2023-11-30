package com.optimagrowth.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RefreshScope //use 'actuator/refresh' to refresh config properties
@EnableFeignClients //enable feign client interfaces
@EnableCaching //enable redis caching
public class LicenseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseProjectApplication.class, args);
    }

}
