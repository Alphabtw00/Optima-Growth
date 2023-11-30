package com.optimagrowth.license.caching;

import com.optimagrowth.license.caching.Component.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConnectionConfig {
    private ServiceConfig serviceConfig;

    @Autowired
    public RedisConnectionConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){  // to connect to redis using jedis connector
        String hostName = serviceConfig.getRedisServer();
        int port = Integer.parseInt(serviceConfig.getRedisPort());
        RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration(hostName, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {   //to serialize and deserialize objects coming and going in redis server
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


}
