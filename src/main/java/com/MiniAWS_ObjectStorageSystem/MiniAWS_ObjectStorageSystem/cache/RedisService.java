package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String,String> redisTemplate;


    public <T> T get (String key,Class<T> entityClass){
        try{
            Object o=redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper=new ObjectMapper();
//            return objectMapper.readValue(o.toString(),entityClass);
            return objectMapper.readValue(o.toString(), new TypeReference<List<entityClass>>() {});
        }catch(NullPointerException nullPointerException){
            throw new NullPointerException("No response received");
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public void set(String key,Object o, Long ttl){
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue=objectMapper.writeValueAsString();
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }

    public

}
