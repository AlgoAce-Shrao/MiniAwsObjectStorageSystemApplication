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
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final ModelMapper modelMapper;
    private final LocalBucketManagementService localBucketManagementService;
    private final BucketRepository bucketRepository;
    private final RedisService redisService;

    public CreateBucketResponseDTO createBucket(String bucketName, AppUser user){
        Bucket bucket=localBucketManagementService.createBucket(bucketName,user);
        return modelMapper.map(bucket,CreateBucketResponseDTO.class);
    }



    public List<GetBucketsDTO> getAllBuckets(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }


//        RedisTemplate<String,String> redisTemplate1=new RedisTemplate<>();
        List<GetBucketsDTO> cached= (List<GetBucketsDTO>) redisService.get(userId.toString(),GetBucketsDTO.class);

        if(cached!=null){
            return cached;
        }else{
            List<GetBucketsDTO> db_response= bucketRepository.findAllByUser_UserId(userId)
                    .stream()
                    .map(bucket -> modelMapper.map(bucket,GetBucketsDTO.class))
                    .toList();
            redisService.set(userId.toString(),db_response,1000*60*10,);
            return db_response;
        }


    }

    public GetBucketDetailsDTO getBucket(Long userId, Long bucketId) {
        if(userId==null || bucketId==null){
            throw new IllegalArgumentException("Credentials  cannot be null");
        }
        Bucket bucket=bucketRepository.findByUser_userIdAndBucketId(userId,bucketId).orElseThrow();
        return modelMapper.map(bucket,GetBucketDetailsDTO.class);
    }
}
