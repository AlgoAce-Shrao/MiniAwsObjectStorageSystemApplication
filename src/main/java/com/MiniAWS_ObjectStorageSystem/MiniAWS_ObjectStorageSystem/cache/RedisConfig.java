package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory factory){   // we connect with the redis db by this connection factory
        RedisTemplate<String, String> redisTemplate=new RedisTemplate<>();

        redisTemplate.setConnectionFactory(factory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());// to ensure the key is stored in string format
        redisTemplate.setValueSerializer(new StringRedisSerializer()); //to ensure the value is also stored in string format

        return redisTemplate;

    }
}
