package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService.LocalBucketManagementService;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketDetailsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.BucketRepository;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final ModelMapper modelMapper;
    private final LocalBucketManagementService localBucketManagementService;
    private final BucketRepository bucketRepository;

    public CreateBucketResponseDTO createBucket(String bucketName, AppUser user){
        Bucket bucket=localBucketManagementService.createBucket(bucketName,user);
        return modelMapper.map(bucket,CreateBucketResponseDTO.class);
    }


    public List<GetBucketsDTO> getAllBuckets(Long userId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

         return bucketRepository.findAllByUser_UserId(userId)
                 .stream()
                 .map(bucket -> modelMapper.map(bucket,GetBucketsDTO.class))
                 .toList();

    }

    public GetBucketDetailsDTO getBucket(Long userId, Long bucketId) {
        if(userId==null || bucketId==null){
            throw new IllegalArgumentException("Credentials  cannot be null");
        }
        Bucket bucket=bucketRepository.findByUser_userIdAndBucketId(userId,bucketId).orElseThrow();
        return modelMapper.map(bucket,GetBucketDetailsDTO.class);
    }
}
