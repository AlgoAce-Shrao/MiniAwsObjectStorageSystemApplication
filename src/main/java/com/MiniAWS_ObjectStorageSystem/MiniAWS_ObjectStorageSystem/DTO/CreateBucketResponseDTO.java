package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;

import lombok.Data;

@Data
public class CreateBucketResponseDTO {

    private Long bucketId;
    private String bucketName;
    private String status;
}
