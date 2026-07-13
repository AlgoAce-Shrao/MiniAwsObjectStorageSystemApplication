package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.controller;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class BucketController {

    private  final BucketService bucketService;

    @PostMapping("/bucket/{bucketName}")
    public ResponseEntity<CreateBucketResponseDTO> createBucket(@PathVariable("bucketName") String bucketName){
        CreateBucketResponseDTO responseDTO=bucketService.createBucket(bucketName);
        responseDTO.setStatus("Bucket created successfully");
        return ResponseEntity.ok(responseDTO);
    }


}
