package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetBucketDetailsDTO {

    private Long bucketId;
    private String bucketName;
    private String bucketPath;
    private LocalDateTime createdAt;
    //will implement count number of files feature
}
