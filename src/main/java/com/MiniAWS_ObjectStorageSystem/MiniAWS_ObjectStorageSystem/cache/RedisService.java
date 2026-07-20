package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;


//    public <T> T get (String key,Class<T> entityClass){
//        try{
//            Object o=redisTemplate.opsForValue().get(key);
//            ObjectMapper objectMapper=new ObjectMapper();
////            return objectMapper.readValue(o.toString(),entityClass);
//            return objectMapper.readValue(o.toString(), new TypeReference<List<entityClass>>() {});
//        }catch(NullPointerException nullPointerException){
//            throw new NullPointerException("No response received");
//        }catch(Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }


//    public void set(String key,Object o, Long ttl){
//        try{
//            ObjectMapper objectMapper=new ObjectMapper();
//            String jsonValue=objectMapper.writeValueAsString();
//            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//    }

    public <T> T get(String key, JavaType javaType){
        try{
            String value=redisTemplate.opsForValue().get(key);

            if(value==null){
                return null;
            }

            return objectMapper.readValue(value,javaType);
        } catch (Exception e) {
            log.error(" Error while reading redis:",e);
            return null;
        }
    }

    public void set(String key,Object value,Long ttl){
        try{
            String json=objectMapper.writeValueAsString(value);

            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(" Error while writing to redis:",e);
        }
    }

}
