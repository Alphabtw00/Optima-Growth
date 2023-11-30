package com.optimagrowth.license.service.feignClient;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean //whcih interceptors to use
    public RequestInterceptor requestInterceptor() {
        return new FeignInterceptor();
    }
}

