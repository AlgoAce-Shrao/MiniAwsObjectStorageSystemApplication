package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.BucketManagementService;


import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.Bucket;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface BucketManagementService {

    Bucket createBucket(String bucketName, @AuthenticationPrincipal AppUser user);
}
