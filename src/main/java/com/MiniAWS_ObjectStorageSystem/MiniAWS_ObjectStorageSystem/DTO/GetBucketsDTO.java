package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetBucketsDTO {

    private Long bucketId;
    private String bucketName;
    private String bucketPath;
    private LocalDateTime createdAt;

}
