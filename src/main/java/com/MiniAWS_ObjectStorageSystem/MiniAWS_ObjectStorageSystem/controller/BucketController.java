package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.controller;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.CreateBucketResponseDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketDetailsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO.GetBucketsDTO;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.entity.AppUser;
import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class BucketController {

    private  final BucketService bucketService;

    @PostMapping("/bucket/{bucketName}")
    public ResponseEntity<CreateBucketResponseDTO> createBucket(@PathVariable("bucketName") String bucketName, @AuthenticationPrincipal AppUser user){

        CreateBucketResponseDTO responseDTO=bucketService.createBucket(bucketName,user);
        responseDTO.setStatus("Bucket created successfully");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/allBuckets")
    public ResponseEntity<List<GetBucketsDTO>> getAllBuckets(@AuthenticationPrincipal AppUser user){
//        System.out.println(authentication);
        return ResponseEntity.ok(bucketService.getAllBuckets(user.getUserId()));
    }

    @GetMapping("/bucket/{bucketId}")
    public ResponseEntity<GetBucketDetailsDTO> getBucket(@AuthenticationPrincipal AppUser user, @PathVariable("bucketId") Long bucketId){
        return ResponseEntity.ok(bucketService.getBucket(user.getUserId(),bucketId));

    }

    


}
