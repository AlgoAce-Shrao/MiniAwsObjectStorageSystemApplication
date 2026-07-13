package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService.LocalBucketManagementService;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final ModelMapper modelMapper;
    private final LocalBucketManagementService localBucketManagementService;

    public CreateBucketResponseDTO createBucket(String bucketName){
        Bucket bucket=localBucketManagementService.createBucket(bucketName);
        return modelMapper.map(bucket,CreateBucketResponseDTO.class);
    }
}
