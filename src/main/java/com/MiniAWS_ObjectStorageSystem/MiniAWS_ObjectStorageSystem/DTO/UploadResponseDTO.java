package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.DTO;


import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UploadResponseDTO {

    private Long fileId;
    private String originalFileName;
    private String storedFileName;
    private Long bucketId;
    @ToString.Exclude
    private String status;
}
