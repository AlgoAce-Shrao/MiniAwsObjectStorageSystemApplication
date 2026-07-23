package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService.LocalBucketManagementService;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketDetailsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.BucketRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.cache.RedisService;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BucketService {

    private final ModelMapper modelMapper;
    private final LocalBucketManagementService localBucketManagementService;
    private final BucketRepository bucketRepository;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    public CreateBucketResponseDTO createBucket(String bucketName, AppUser user){
        Bucket bucket=localBucketManagementService.createBucket(bucketName,user);
        return modelMapper.map(bucket,CreateBucketResponseDTO.class);
    }



    public List<GetBucketsDTO> getAllBuckets(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        String cacheKey="user:"+userId+":buckets";

        JavaType javaType=objectMapper.getTypeFactory().constructCollectionType(List.class,GetBucketsDTO.class);

        List<GetBucketsDTO> cached=redisService.get(cacheKey,javaType);

        if(cached!=null){
            log.info(" cache hit");
            return cached;
        }

        log.info(" cache miss..so will hit the db");
        List<GetBucketsDTO> response =
                bucketRepository.findAllByUser_UserId(userId)
                        .stream()
                        .map(bucket ->
                                modelMapper.map(
                                        bucket,
                                        GetBucketsDTO.class
                                ))
                        .toList();

        redisService.set(cacheKey,response,600L);

        return response;
    }

    public GetBucketDetailsDTO getBucket(Long userId, Long bucketId) {
        if(userId==null || bucketId==null){
            throw new IllegalArgumentException("Credentials  cannot be null");
        }

        String cacheKey="user:"+userId+":buckets";

        JavaType javaType=objectMapper.getTypeFactory().constructCollectionType(List.class,GetBucketsDTO.class);

        List<GetBucketsDTO> cached=redisService.get(cacheKey,javaType);

        if(cached!=null){
            log.info("Cache hit again");
            for(GetBucketsDTO bucketsDTO:cached){
                if(Objects.equals(bucketsDTO.getBucketId(), bucketId)) return modelMapper.map(bucketsDTO,GetBucketDetailsDTO.class);
            }
        }
        Bucket bucket=bucketRepository.findByUser_userIdAndBucketId(userId,bucketId).orElseThrow();
        return modelMapper.map(bucket,GetBucketDetailsDTO.class);
    }
}
